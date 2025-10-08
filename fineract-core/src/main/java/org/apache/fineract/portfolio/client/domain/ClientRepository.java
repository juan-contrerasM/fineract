/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements. See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership. The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.apache.fineract.portfolio.client.domain;

import org.apache.fineract.infrastructure.core.domain.ExternalId;
import org.apache.fineract.portfolio.client.domain.search.SearchingClientRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ClientRepository extends JpaRepository<Client, Long>, JpaSpecificationExecutor<Client>, SearchingClientRepository {

    String FIND_CLIENT_BY_ACCOUNT_NUMBER = "select client from Client client where client.accountNumber = :accountNumber";

    @Query(FIND_CLIENT_BY_ACCOUNT_NUMBER)
    Client getClientByAccountNumber(@Param("accountNumber") String accountNumber);

    @Query("""
            SELECT client
            FROM Client client
            JOIN client.office office
            LEFT JOIN client.transferToOffice transferToOffice
            WHERE client.id = :clientId
            AND (office.hierarchy LIKE :officeHierarchy OR transferToOffice.hierarchy LIKE :transferToOfficeHierarchy)
                """)
    Client fetchByClientIdAndHierarchy(@Param("clientId") Long clientId, @Param("officeHierarchy") String officeHierarchy,
            @Param("transferToOfficeHierarchy") String transferToOfficeHierarchy);

    @Query("SELECT c.id FROM Client c WHERE c.externalId = :externalId")
    Long findIdByExternalId(@Param("externalId") ExternalId externalId);

    /**
     * Repositorio para acceder a clientes con saldo negativo.
     *
     * @return Lista de clientes que tienen préstamos con saldo vencido.
     */
    @Query(value = """
    SELECT DISTINCT c.*
    FROM m_client c
    WHERE EXISTS (
        SELECT 1
        FROM m_loan l
        JOIN m_loan_arrears_aging laa ON laa.loan_id = l.id
        WHERE l.client_id = c.id
          AND c.status_enum = 300
          AND l.loan_status_id = 300
          AND total_overdue_derived > 0
    )
    """, nativeQuery = true)
    List<Client> retrieveClientsWithNegativeBalance();

    /**
     * Repositorio para obtener el top 3 de clientes con mayor balance
     *
     * @return Top 3  de clientes que tienen préstamos con saldo vencido.
     */
    @Query(value = """
    SELECT c.*
    FROM m_client c
    WHERE c.id IN (
        SELECT client_id
        FROM (
            SELECT sa.client_id, SUM(sa.account_balance_derived) AS total_balance
            FROM m_savings_account sa
            WHERE sa.status_enum = 300
            AND c.status_enum = 300
            GROUP BY sa.client_id
            ORDER BY total_balance DESC
            LIMIT 3
        ) AS top_clients
    )
    """, nativeQuery = true)
    List<Client> retrieveTopClientsByBalance();



    /**
     * Repositorio para obtener el top 3 de clientes con balance engativo
     *
     * @return Lista de clientes con saldo negativo.
     */
    @Query(value = """
    SELECT DISTINCT c.*
    FROM m_client c
    WHERE EXISTS (
        SELECT 1
        FROM m_savings_account sa
        WHERE sa.client_id = c.id
          AND sa.status_enum = 300 
          AND c.status_enum = 300 
          AND sa.account_balance_derived < 0
    )
    """, nativeQuery = true)
    List<Client> retrieveClientsWithNegativeSavingsBalance();

}
