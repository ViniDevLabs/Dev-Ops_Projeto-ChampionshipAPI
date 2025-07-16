Para reservar um ip estático no Google Cloud, foi executado o seguinte comando:

`gcloud compute addresses create championship-api-static-ip --global`

e no Ingress do projeto, adicionamos a seguinte annotation:

```yaml
metadata:
  name: championship-api-ingress
  .
  .
  .
  annotations:
    kubernetes.io/ingress.global-static-ip-name: "championship-api-static-ip"
```