variable "project_id" {
  description = "ID do projeto no Google Cloud Platform"
  type        = string
  default     = "ufrn-devops-2025-01"
}

variable "region" {
  description = "A região do deploy"
  type        = string
  default     = "us-central1"
}

variable "cluster_name" {
  description = "Cluster do Google Kubernetes Engine"
  type        = string
  default     = "devops-cluster-1"
}