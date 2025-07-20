# Configurações do Kubernetes

- A aplicação está implantada no Google Kubernetes Engine (GKE), que é o serviço gerenciado de Kubernetes da Google Cloud.
- Utiliza-se o serviço de DNS DuckDNS para que a aplicação seja acessível via Ingress, pelo link [http://championship-api.duckdns.org/](http://championship-api.duckdns.org/)
- Execute o comando `kubectl apply -f championship-api.yaml` para aplicar os manifestos do projeto


Para expor a aplicação com um endereço fixo e garantir que o IP externo não mude em reinicializações do cluster, foi reservado um IP estático global no Google Cloud com o comando:

`gcloud compute addresses create championship-api-static-ip --global`

e no Ingress do projeto, para vincular o IP estático criado, adicionamos a seguinte annotation:

```yaml
metadata:
  name: championship-api-ingress
  annotations:
    kubernetes.io/ingress.global-static-ip-name: "championship-api-static-ip"
```

## Banco de Dados

O banco de dados PostgreSQL é configurado usando os seguintes recursos para garantir que os dados sejam armazenados de forma segura e persistente.

- Secret (championship-api-postgres-secret): Armazena o usuário e a senha do banco de dados de forma segura (codificados em Base64).
- StorageClass (balanced): Define um tipo de disco de armazenamento (neste caso, pd-balanced no GKE) que será criado dinamicamente para o banco de dados.
- Service (championship-api-postgres): Cria um nome de rede interno e estável (championship-api-postgres) para que a aplicação possa se conectar ao banco de dados sem precisar saber seu IP.
- StatefulSet (championship-api-postgres-statefulset): Gerencia o Pod do PostgreSQL. Ele garante que o banco de dados tenha um armazenamento persistente e exclusivo, além de configurar verificações de saúde (probes) para monitorar se o banco está funcionando corretamente, utilizando a função "pg_is_ready" do próprio PostgreSQL.

## Aplicação

A API principal é gerenciada pelos seguintes recursos para garantir alta disponibilidade e acesso externo.

- Deployment (championship-api-deployment): Gerencia os Pods da aplicação. É configurado para rodar 3 réplicas da API, garantindo escalabilidade e resiliência. Inclui um initContainer que aguarda o banco de dados ficar pronto antes de iniciar a aplicação, de maneira a evitar reinícios precoces por parte do startupProbe. Também utiliza os endpoints "/actuator/health/liveness" e "/actuator/health/readiness", fornecidos pelo Spring Boot Actuator, nas verificações de saúde (probes).
- Service (championship-api-service): Cria um ponto de acesso interno para as réplicas da API. Ele agrupa os Pods do Deployment e distribui o tráfego entre eles.
- Ingress (championship-api-ingress): Expõe a aplicação para a internet. Ele mapeia o endereço público (championship-api.duckdns.org) para o Service da aplicação, permitindo que usuários externos acessem a API. Está configurado para usar um IP estático global.