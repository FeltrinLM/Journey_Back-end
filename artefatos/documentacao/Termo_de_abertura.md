# Termo de abertura do projeto

Nome do Projeto: Sistema de gerenciamento de estoque Journey
Cliente : Vicenzo Mendes
Patrocinador do Projeto: Vicenzo Mendes
Gerente do Projeto: Lorenzo Feltrin
Previsão de Início: 09/07/2025

Previsão de Término: 01/12/2025

## Descrição da motivação e justificativa:

O projeto surgiu com o objetivo de resolver a falta de um sistema de controle de estoque na empresa Journey, que atualmente não possui uma forma estruturada de organizar seus produtos. Com o crescimento da marca, tornou-se inviável continuar gerenciando o estoque de forma manual ou por observação visual, evidenciando a necessidade de um sistema estruturado.

A ideia do sistema nasceu a partir de um trabalho acadêmico, no qual era proposto o desenvolvimento de uma aplicação web. Aproveitando a oportunidade, decidiu-se criar uma solução que também pudesse atender a uma demanda real da empresa do meu irmão, contribuindo diretamente com a organização e profissionalização do negócio.

A implantação desse sistema proporcionará ao usuário uma gestão mais eficiente e segura, acompanhando o crescimento da marca e oferecendo maior controle sobre os itens em estoque.

## Objetivo (SMART):

Desenvolver, em até 5 meses, um sistema web de gerenciamento de estoque para a empresa Journey. A aplicação deve permitir login e cadastro de usuários com dois níveis de permissão (funcionário e administrador), controle de ações conforme o perfil, visualização do histórico de alterações, cadastro e edição de peças, coleções, estampas, chaveiros e adesivos, além da prevenção de duplicidade de itens, oferecendo uma interface intuitiva e compatível com computadores desktop.

Para garantir a segurança e o controle de acesso desde o início, o sistema deverá criar automaticamente um **usuário administrador padrão** na primeira execução, caso não exista nenhum usuário registrado. O cadastro de novos usuários será restrito a administradores autenticados, evitando cadastros públicos.

## Descrição Resumida do Projeto:

O projeto visa desenvolver um sistema web de gerenciamento de estoque para a empresa Journey. A aplicação permitirá o controle de peças, coleções, estampas, chaveiros e adesivos, com funcionalidades de login, cadastro de usuários e controle de permissões. O sistema contará com dois níveis de acesso (funcionário e administrador), interface intuitiva para computadores e uma página de histórico que registra as alterações feitas nas tabelas, respeitando o nível de permissão de cada usuário.

Como medida de segurança, o sistema criará automaticamente um **usuário administrador padrão** na primeira execução do sistema (caso a base de usuários esteja vazia), garantindo que o controle de acesso inicial não dependa de cadastro público. A criação de novos usuários será permitida apenas a administradores já autenticados.

## Entregas principais (requisitos):

- **(RF)** O sistema deve incluir um mecanismo de login
- **(RF)** O sistema deve permitir o cadastro de novos usuários apenas por administradores autenticados
- **(RF)** O sistema deve criar automaticamente um usuário administrador padrão na primeira execução, caso nenhum usuário esteja registrado
- **(RF)** O sistema deve apresentar visões diferentes com base nas permissões do usuário
- **(RF)** O sistema deve permitir a visualização de todas as peças, coleções, estampas, chaveiros e adesivos do estoque da Journey
- **(RF)** O sistema deve permitir a exclusão e edição de itens do estoque
- **(RF)** O sistema deve impedir a existência de itens duplicados nas tabelas
- **(RF)** O sistema deve possuir uma página de histórico, onde será possível visualizar todas as alterações feitas nas tabelas
- **(RN)** O sistema deve admitir dois níveis de permissão de usuário: Funcionário e Administrador
- **(RN)** Funcionários podem adicionar e editar itens; Administradores podem adicionar, editar e remover itens
- **(RNF)** O sistema deve rodar na web
- **(RNF)** O sistema deve funcionar em computadores, não sendo necessário ser responsivo para dispositivos móveis
- **(RNF)** A interação dentro do sistema deve ser intuitiva

## Recursos pré-alocados(quantos e quais recursos serão fornecidos):

Um único desenvolvedor será responsável por todas as etapas do projeto, desde o planejamento até a implementação e testes.

**Tempo de dedicação estimado** de aproximadamente **5 meses**, com carga horária variável conforme a disponibilidade do desenvolvedor.

**Equipamento pessoal**: **desktop** com ambiente de desenvolvimento configurado.

**Ferramentas gratuitas utilizadas**: **IntelliJ IDEA (versão educacional), Figma, PostgreSQL, Spring Boot** e **GitHub**.

**Acesso direto ao usuário final** para coleta de requisitos, validação e feedback contínuo.

## Stakeholders:

Lorenzo Feltrin (gerente do projeto)

Vicenzo Porto Mendes (Cliente)

Alencar Machado (avaliador acadêmico)

## Premissas e restrições:

**Premissas:**
- O cliente (ou usuário final) estará disponível para fornecer feedback ao longo do desenvolvimento.
- A conexão com o banco de dados será estável e acessível durante os testes.
- O desenvolvedor terá disponibilidade semanal para se dedicar ao projeto.
- Os requisitos definidos inicialmente não sofrerão alterações drásticas.
- O sistema será utilizado em ambientes desktop, não exigindo responsividade para dispositivos móveis.

**Restrições:**
- O sistema deve ser entregue no prazo máximo de 5 meses.
- O projeto será desenvolvido por apenas uma pessoa.
- Apenas ferramentas gratuitas ou com licenças educacionais poderão ser utilizadas.
- O sistema deve funcionar via navegador (aplicação web-based).
