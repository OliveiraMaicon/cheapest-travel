# API que define melhor rota em custo benefício.

* Para desenvolver essa lógica foi utilizado o algoritimo de Dijkstra.

 O empacotamento é dividido pelas responsabilidades de cada camada, ou seja:
 
 > view : Camada no padrão RestFull responsável por tratar as request, com validações, padronizações de responses. 
 
 > domain/service Camada responsável gerenciar dados, seja ela sendo BD ou APIs externas.
 
 > application: Camada responsável pelas execuções de negócio, podendo interagir com outras applications e também a camada de service.
 
 > infraestructure: Camada responsável pro prover classes bases para aplicação, como configs, utils, crons, exceptions e etc...



### Pré-requisitos ###

[Java 8](http://www.oracle.com/technetwork/pt/java/javase/downloads/jdk8-downloads-2133151.html)

[Kotlin](https://kotlinlang.org/docs/reference/server-overview.html)

[Gradle](https://gradle.org/install/)

[Docker](https://docs.docker.com/engine/installation/)

[Kubernetes](https://kubernetes.io/)


### Executando o Projeto - Local ###

_Caso não tenha o gradle instalado na maquina pode utilizar o embbedado no projeto './gradlew'_

Build:

    gradle build  

Run:
    
    gralde bootRun

Open:

    http://127.0.0.1:8080

-----------------------------

### Executando o Projeto - Docker ###

Build do projeto:

    gradle build     

Gerando a imagem Docker:

    docker build -t cheapest-travel .

Listar as imagens geradas:
    
    docker images 
    
E executar:       
    
    docker run -d -p 8080:8080 cheapest-travel

Visualizar container:

    docker ps

Se não subir, veja os logs:

    docker logs <CONTAINER_ID>

Para acessar:
    
    http://127.0.0.1:8080
    
Para parar:

    docker rm -f <CONTAINER_ID>

-----------------------------


### Deploy do projeto - Kubernetes ###

Logar no registry ou no Docker hub, exemplo:

    docker login outroregistry.com.br -u ${USER}
    
    docker login -u ${USER}
    
Build do projeto
    
    gradle build
    
Gerando a imagem Docker:

Imagem para DockerHub
    
    docker build -t cheapest-travel .
    
Imagem para outro registry:

    docker build -t outroregistry.com.br/cheapest-travel:latest 
    
Push para Registry/Docker hub

    docker push <NOME_IMAGEM> #(outroregistry.com.br/cheapest-travel:latest | cheapest-travel:latest)
    
    
Aplicando resources(executar na raiz do repositório)

    kubectl apply -f src/main/kubernetes/configmap.yml
    
    kubectl apply -f src/main/kubernetes/deployment.yml
    
    kubectl apply -f src/main/kubernetes/service.yml
    
Como acessar(via cluster-ip) ?

    kubect get svc 


 ### Mais informações? ###
 
 Fale com mikon182@gmail.com   
