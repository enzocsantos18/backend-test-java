# 🚘 API Estacionamento
---
Api para controle de estacionamentos, onde uma empresa pode ter todos seus estacionamentos cadastrados de forma individual tirando proveito da criação de usuários com diferentes funções administrativas, controle a entrada e saída de veículos, geração relatórios de movimentações, etc...

## Documentação da API
---
Clique nesse link para ter acesso a documentação: [https://documenter.getpostman.com/view/9120629/TzkyP1AY](https://documenter.getpostman.com/view/9120629/TzkyP1AY)


## Tecnologias
---
- [Java com o framework Spring Boot] - API
- [JUnit e Mockito] - Testes automátizados
- [Token JWT] - Autenticação
- [Mysql] - Banco de dados


## Instalação
requisitos: Java 16 e Maven 3.81

1. Clone o projeto em sua máquina através do comando abaixo:

```sh
git clone https://github.com/enzocsantos18/backend-test-java.git
```
2. Vá no seu mysql e crie um database chamado sistema_estacionamento com o seguinte comando: 

```sh
CREATE DATABASE sistema_estacionamento;
```
3. Com o database criado, entre na pasta do projeto e procure pelo arquivo applicationSettings, e altere as configurações de acesso ao banco, o arquivo estará nesse formato:
```sh
spring.jpa.hibernate.ddl-auto=update
spring.datasource.url=jdbc:mysql://localhost:3306/sistema_estacionamento
spring.datasource.username=root
spring.datasource.password=root
spring.datasource.driver-class-name =com.mysql.jdbc.Driver

# jwt
forum.jwt.secret=rm'!@N=Ke!~p8VTA2ZRK~nMDQX5Uvm!m'D&]{@Vr?G;2?XhbC:Qa#9#eMLN\}x3?JR3.2zr~v)gYF^8\:8>:XfB:Ww75N/emt9Yj[bQMNCWwW\J?N,nvH.<2\.r~w]*e~vgak)X"v8H`MH/7"2E`,^k@n<vE-wD3g9JWPy;CrY*.Kd2_D])=><D?YhBaSua5hW%{2]_FVXzb9`8FH^b[X3jzVER&:jw2<=c38=>L/zBq`}C6tT*cCSVC^c]-L}&/
forum.jwt.expiration=86400000
```

4. Agora será necessário rodar o maven a instalação do maven na pasta \estacionamento presente no projeto através da seguinte linha de comando
``` sh
mvn clean install
```
5. Agora você pode buildar o projeto, com o seguinte comando:
``` sh
mvn package
```

6. Agora para rodar o projeto rode o seguinte comando:
``` sh
java -jar target/estacionamento-0.0.1-SNAPSHOT.jar br.com.estacionamento.EstacionamentoApplication
```

7. Antes de executar as funções fornecidas pela api, rode a massa de dados fornecida no arquivo inserts.sql, ou copie e cole em seu sql os inserts abaixo: 
```sh 
insert into tipo_usuario values (1,"admin");
insert into tipo_usuario values (2,"admin_estacionamento");
insert into tipo_usuario values (3,"funcionario");

insert into tipo_veiculo values (1,"Carro");
insert into tipo_veiculo values (2,"Moto");

insert into fabricante values (1,"Chevrolet");
insert into modelo values (1, "Onix", 1, 1);

insert into fabricante values (2,"Fiat");
insert into modelo values (2, "Toro", 2, 1);

insert into fabricante values (3,"Ford");
insert into modelo values (3, "Ka", 3, 1);

insert into fabricante values (4,"Volkswagen");
insert into modelo values (4, "Fox", 4, 1);

insert into fabricante values (5,"BMW");
insert into modelo values (5, "X6", 5, 1);

insert into fabricante values (6,"Audi");
insert into modelo values (6, "m4", 6, 1);

insert into fabricante values (7,"Mercedes");
insert into modelo values (7, "c180 coupé", 7, 1);

insert into fabricante values (8,"Hyundai");
insert into modelo values (8, "Creta", 8, 1);

insert into fabricante values (9,"Toyota");
insert into modelo values (9, "Corolla", 9, 1);

insert into fabricante values (10,"Honda");
insert into modelo values (10, "Civic", 10, 1);
insert into modelo values (11, "CG", 10, 2);

insert into fabricante values (11,"Triumph");
insert into modelo values (12, "Speed Twin", 11, 2);
```

