Histórico de Revisões:
10/07 - Início do Projeto:
Nossa equipe inteira estava junta e deu início ao desenvolvimento do sistema de estacionamento. Neste dia, foram definidos os requisitos básicos do projeto, como a estrutura de microsserviços e a configuração inicial do ambiente Spring Boot. O repositório foi criado e organizado para abrigar os futuros módulos do sistema.

12/07 - Modelagem Inicial dos Dados (Com Jonatha e Klynsmyanno)
Os trabalhos avançaram com a criação das primeiras entidades do sistema: Veiculo, Vaga e Ticket. Também demos início ao serviço de descoberta (Eureka Server), essencial para a comunicação entre os microsserviços. Essas definições iniciais estabeleceram a base para a persistência e o gerenciamento dos dados do estacionamento.

13/07 - Implementação dos Repositórios
Com os modelos de dados prontos, nós focamos na camada de acesso ao banco de dados, implementando as interfaces TicketRepository, VeiculoRepository e VagaRepository utilizando Spring Data JPA. Paralelamente, começamos a desenvolver a lógica de negócio no serviço principal (EstacionamentoService), que orquestrava as operações do sistema.

14/07 - Criação dos Controladores 
Para expor as funcionalidades via API REST, foram desenvolvidos os controladores TicketController e VeiculoController. Esses endpoints permitiriam operações básicas, como registrar veículos, gerar tickets e listar dados. Seguimos os princípios RESTful, utilizando verbos HTTP para cada ação.

16/07 - Inicialização de Dados e Refinamento (Com Jonatha e Klynsmyanno)
A automatização da criação de vagas foi implementada através da classe DataInitializer, que, ao iniciar a aplicação, verificava a existência de vagas no banco e criava 10 vagas padrão (de "A1" a "A10") se necessário. Além disso, a lógica de negócio para registro e finalização de tickets foi aprimorada para garantir consistência nas operações.

17/07 - Tratamento de Exceções e Serialização
Para melhorar a robustez do sistema, introduzimos exceções personalizadas (EstacionamentoException) e tratamos casos como vagas indisponíveis ou tickets já finalizados. Também resolvemos problemas de serialização JSON com as anotações @JsonManagedReference e @JsonBackReference, evitando referências circulares nas respostas da API.

19/07 - Testes e Documentação Preliminar (Com Jonatha e Klynsmyanno)
Neste dia, eles fizeram testes manuais nos endpoints para validar o fluxo completo do sistema, desde o registro de um veículo até a geração e finalização de tickets. Bugs identificados foram corrigidos.

20/07 - Cálculo Automático de Valores
A funcionalidade de cálculo do valor do ticket foi implementada, considerando o tempo de permanência do veículo e arredondando para a próxima hora cheia. A lógica de disponibilidade de vagas também foi ajustada para refletir corretamente o status (ocupado/disponível) durante as operações. E uma documentação inicial da arquitetura e dos modelos de dados foi elaborada para referência futura.

21/07 - Revisão e Refatoração (Com Jonatha e Klynsmyanno)
Com a maior parte do sistema funcional, eles se dedicaram a revisar o código, aplicando boas práticas como clean code e melhorando a organização das classes. A documentação técnica foi atualizada para refletir as últimas alterações e facilitar a manutenção.

22/07 - Mais Revisão
Neste dia tentamos finalizar o projeto e a documentação, porém ainda estávamos um pouco longe do esperado. Mas conseguimos dar um grande passo.

22/07 - Finalização e Preparação para Deploy
No último dia de desenvolvimento, o relatório técnico foi concluído, detalhando a arquitetura, padrões utilizados e funcionalidades do sistema. A aplicação foi implantada em um ambiente de teste para validação final, marcando a conclusão do projeto pelo time.
Introdução
Este relatório tem como propósito apresentar uma visão clara e estruturada da arquitetura de software desenvolvida para o sistema de gerenciamento de estacionamento. O foco principal está na descrição dos componentes que integram a aplicação, os serviços envolvidos e os padrões de arquitetura adotados, com ênfase na aplicação do framework Spring Boot e no modelo MVC (Model-View-Controller).
Arquitetura dos Serviços
Serviço de Descoberta (DiscoveryApplication)
Localizado na pasta discovery, este componente funciona como um servidor Eureka. Sua principal responsabilidade é permitir que os demais microsserviços se registrem e se reconheçam mutuamente, tornando possível a comunicação entre as diferentes partes da aplicação. Esse tipo de serviço é essencial em arquiteturas baseadas em microsserviços, onde a descentralização e a autonomia dos módulos exigem uma forma eficiente de integração entre eles.
Serviço de Estacionamento
A lógica principal do sistema está concentrada na classe EstacionamentoService, encontrada em estacionamento.service. Essa classe é o núcleo do que chamamos de “camada de serviço”, sendo responsável por coordenar operações como registro de veículos, geração e finalização de tickets, além do gerenciamento de vagas.
Alguns dos métodos de destaque são:
registrarVeiculo(Veiculo veiculo): Realiza o registro de entrada de um veículo. O sistema procura uma vaga disponível, vincula o veículo a essa vaga e salva as informações. Caso todas as vagas estejam ocupadas, uma exceção é lançada.
listarVeiculos(): Retorna todos os veículos atualmente cadastrados.
deletarVeiculo(Long id): Remove um veículo específico com base no seu ID.
gerarTicket(Long veiculoId): Gera um novo ticket, associando-o ao veículo e criando um código único para controle.
finalizarTicket(Long ticketId): Marca a saída de um veículo, calcula o valor a ser pago com base no tempo de permanência e libera a vaga ocupada.
calcularValor(Long ticketId): Realiza o cálculo do valor do ticket, arredondando o tempo de permanência para a próxima hora cheia, conforme a regra de negócio.
listarTickets(): Lista todos os tickets gerados no sistema.
Além do serviço em si, o sistema também conta com dois controladores:
TicketController
Responsável por todas as operações relacionadas aos tickets. Entre os endpoints expostos, destacam-se:
POST /tickets/{veiculoId} – Criação de um novo ticket para um veículo.
GET /tickets/{ticketId}/valor – Cálculo do valor de um ticket específico.
GET /tickets – Listagem de todos os tickets.
PUT /tickets/{ticketId}/finalizar – Finalização de um ticket.
VeiculoController
Controla as ações ligadas ao cadastro e gerenciamento de veículos:
POST /veiculos – Registro de um novo veículo no sistema.
GET /veiculos – Consulta de todos os veículos registrados.
DELETE /veiculos/{id} – Remoção de um veículo com base em seu ID.
Inicialização de Dados
Para garantir que o sistema esteja pronto para uso assim que for iniciado, foi implementada uma rotina automática de configuração inicial. Essa lógica está contida na classe DataInitializer, localizada na pasta estacionamento.data.
A classe utiliza a interface CommandLineRunner do Spring Boot, que permite executar um bloco de código assim que a aplicação é carregada. O papel principal dessa rotina é verificar se já existem vagas cadastradas no banco de dados. Caso não haja, ela cria automaticamente 10 vagas, identificadas de “A1” a “A10”, todas marcadas como disponíveis. Essa estratégia facilita tanto o processo de testes quanto a experiência do usuário nos primeiros usos do sistema.
Tratamento de Exceções
Para lidar com situações inesperadas ou regras de negócio violadas, foi criada a classe EstacionamentoException, localizada na pasta estacionamento.exception.
Trata-se de uma exceção personalizada que estende RuntimeException, permitindo lançar erros específicos relacionados ao domínio da aplicação — por exemplo, quando não há mais vagas disponíveis ou quando se tenta finalizar um ticket já encerrado. Ter essa exceção customizada contribui para uma melhor organização do código e facilita a comunicação de falhas de forma clara e consistente.
Modelos de Dados
O sistema trabalha com três modelos principais: Ticket, Vaga e Veículo. Esses modelos representam as entidades centrais da aplicação e estão localizados na pasta model.
1. Modelo Ticket
O Ticket representa o comprovante de entrada e saída de um veículo no estacionamento, servindo como base para o cálculo do valor a ser cobrado.
Atributos principais:
id: Identificador único do ticket.
codigo: Código único gerado para cada ticket.
veiculo: Referência ao veículo associado (relação muitos-para-um).
horaEntrada: Momento em que o veículo entrou no estacionamento.
horaSaida: Momento da saída (opcional, pois só é preenchido ao final).
valor: Valor final calculado com base no tempo de permanência.


Relações:
Um mesmo veículo pode ter vários tickets associados ao longo do tempo.
2. Modelo Vaga
A Vaga representa um espaço físico onde os veículos podem estacionar.
Atributos principais:
id: Identificador único.
numero: Identificação da vaga (ex.: "A3").
disponivel: Indica se a vaga está atualmente livre.
veiculo: Veículo que está ocupando a vaga (relação um-para-um).
Relações:
Cada vaga só pode estar ocupada por um único veículo.
Usa a anotação @JsonManagedReference para evitar problemas de referência circular na serialização JSON.
3. Modelo Veículo
O Veiculo representa os carros (ou outros meios de transporte) que acessam o estacionamento.
Atributos principais:
id: Identificador único.
placa: Placa do veículo (única e obrigatória).
modelo: Modelo do veículo.
horaEntrada: Horário em que o veículo entrou.
horaSaida: Horário de saída (opcional).
vaga: Vaga atualmente ocupada pelo veículo (um-para-um).
Relações e observações:
Um veículo só pode ocupar uma vaga por vez.
Utiliza @JsonBackReference para evitar ciclos na serialização.
Possui o método registrarEntrada(), que grava a hora atual como entrada e evita registros duplicados.
Camada de Persistência (Repositórios)
A persistência dos dados no sistema é gerenciada por meio do Spring Data JPA, o que permite uma grande economia de tempo e esforço no desenvolvimento. Os repositórios ficam localizados na pasta repository e são responsáveis por intermediar a comunicação entre a aplicação e o banco de dados.
Principais repositórios:
TicketRepository
 Interface que estende JpaRepository<Ticket, Long>, fornecendo todos os métodos básicos de CRUD (Create, Read, Update, Delete) para a entidade Ticket. Com isso, não é necessário escrever implementações manuais para a maioria das operações.
VeiculoRepository
 Também estende JpaRepository<Veiculo, Long>, mas com um diferencial: inclui o método countByHoraEntradaIsNotNullAndHoraSaidaIsNull(), que serve para contar quantos veículos estão estacionados no momento — ou seja, os que têm hora de entrada registrada, mas ainda não possuem hora de saída.
VagaRepository
 Assim como os demais, oferece as funcionalidades de CRUD para a entidade Vaga, permitindo consultas e atualizações diretas nas vagas disponíveis no estacionamento.
Padrões de Arquitetura
O sistema segue uma arquitetura bem definida, utilizando conceitos modernos que priorizam organização, escalabilidade e manutenção facilitada.
Framework MVC (Model-View-Controller)
A aplicação é estruturada segundo o modelo MVC adaptado ao Spring Boot, utilizando o subpadrão Controller-Service-Repository.
Controller (C)
 Responsável por receber as requisições HTTP. Exemplo: TicketController e VeiculoController, que expõem os endpoints da API.
Service
 Intermediário entre os controladores e os repositórios. Exemplo: EstacionamentoService, onde está concentrada toda a lógica de negócio.
Model (M)
 Representado pelas classes Ticket, Veiculo e Vaga, que definem as estruturas de dados manipuladas pela aplicação.
View (V)
 Como se trata de uma API REST, a “view” é a resposta enviada ao cliente — geralmente em formato JSON —, e pode ser consumida por aplicações web, mobile ou outros serviços.


Microsserviços
O sistema adota uma arquitetura baseada em microsserviços. Isso pode ser observado principalmente pela presença do DiscoveryApplication (servidor Eureka), que permite que cada serviço funcione de forma independente e se comunique com os demais. Essa abordagem traz benefícios como escalabilidade, maior resiliência e facilidade de manutenção, já que é possível evoluir partes específicas da aplicação sem impactar o sistema inteiro.
RESTful API
Os controladores seguem o padrão REST, com o uso adequado dos verbos HTTP (GET, POST, PUT, DELETE) e endpoints bem definidos para cada recurso. Isso garante que a comunicação com a aplicação seja intuitiva e compatível com boas práticas do desenvolvimento web.
Padrões de Projeto
Diversos padrões de projeto foram aplicados ou inferidos na construção do sistema, o que demonstra um cuidado com a organização do código e a separação adequada de responsabilidades.
Injeção de Dependência
Com o uso da anotação @RequiredArgsConstructor do Lombok, o Spring realiza automaticamente a injeção dos serviços nas classes que precisam deles. Por exemplo, tanto o TicketController quanto o VeiculoController recebem o EstacionamentoService dessa forma. Essa prática reduz o acoplamento entre as classes e facilita a realização de testes unitários.
Singleton (implícito pelo Spring)
Embora o padrão Singleton não tenha sido declarado explicitamente, o próprio Spring gerencia os serviços e controladores como singletons por padrão. Ou seja, uma única instância de cada componente é criada e reutilizada durante o ciclo de vida da aplicação, otimizando o desempenho e o uso de recursos.
Controller
O padrão Controller é utilizado por TicketController e VeiculoController, que centralizam o tratamento das requisições e direcionam cada chamada para a lógica apropriada no serviço correspondente.
Service (Camada de Serviço)
EstacionamentoService representa claramente o padrão de Camada de Serviço. Essa classe concentra a lógica de negócio, mantendo os controladores focados apenas na orquestração de chamadas e na preparação de respostas.
Repository
As interfaces TicketRepository, VeiculoRepository e VagaRepository seguem o padrão Repository. Esse padrão tem como objetivo abstrair a lógica de acesso a dados, permitindo que o restante da aplicação trabalhe com os dados sem se preocupar com os detalhes da persistência. O Spring Data JPA facilita ainda mais essa implementação, fornecendo comportamentos prontos e personalizáveis.











