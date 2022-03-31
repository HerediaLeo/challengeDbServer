# challengeDbServer:
Desafio DBServer - Sicredi

# Explicação do Problema:
Cenário de Negócio:
Todo dia útil por volta das 6 horas da manhã um colaborador da retaguarda do Sicredi recebe e organiza as informações de
contas para enviar ao Banco Central. Todas agencias e cooperativas enviam arquivos Excel à Retaguarda. Hoje o Sicredi
já possiu mais de 4 milhões de contas ativas.
Esse usuário da retaguarda exporta manualmente os dados em um arquivo CSV para ser enviada para a Receita Federal,
antes as 10:00 da manhã na abertura das agências.

Requisito:
Usar o "serviço da receita" (fake) para processamento automático do arquivo.

Funcionalidade:
0. Criar uma aplicação SprintBoot standalone. Exemplo: java -jar SincronizacaoReceita <input-file>
1. Processa um arquivo CSV de entrada com o formato abaixo.
2. Envia a atualização para a Receita através do serviço (SIMULADO pela classe ReceitaService).
3. Retorna um arquivo com o resultado do envio da atualização da Receita. Mesmo formato adicionando o resultado em uma
   nova coluna.


Formato CSV:
agencia;conta;saldo;status
0101;12225-6;100,00;A
0101;12226-8;3200,50;A
3202;40011-1;-35,12;I
3202;54001-2;0,00;P
3202;00321-2;34500,00;B

# Solução:

O java apresenta diversas bibliotecas para Leitura de arquivos, desta forma,
foi escolhida a biblioteca OpenCSV para fazer a leitura do arquivo e escrita.

A leitura do arquivo pode ser feita tanto por Arrays de Strings, tanto quanto
popular diretamente as classes modelos através de anotações, a solução para leitura
escrita foi a segunda, utilizando o "Bean" para extrair os dados.

Foi modelada uma classe Conta utilizando a anotação @CsvBindByName, na qual a API
OpenCsv consegue modelar o atributo da classe utilizando o mesmo nome das colunas, mas 
também há outras diversas formas de modelagem, tais como: por posição do CSV,
colocar o nome do atributo diferente e passar o nome da coluna na anotação. Neste ponto
a api é bem completa.

Para a leitura, processamento e gravação do arquivo foram criados diferentes
serviços seguindo o principio S (single responsability) do SOLID.

Conseguir ler os dados e mapea-los é aproximadamente 90% do problema.
Após a leitura, a Biblioteca te permite retornar uma lista de objetos já 
populados.

Com essa lista em mãos é possível chamar o serviço de atualização da receita
e fazer a validação dos dados.

Na parte de escrita, segue o mesmo principio da Leitura, a biblioteca
permite gravar um arquivo a partir de uma lista de um Bean já mapeado(ContaModel.class).
Com a lista de contas populadas, ao chamar a escrita é criado um arquivo. É necessário
passar somente o caminho aonde o arquivo vai ser gerado.
Caso queira mudar o delimitador do novo arquivo CSV gerado, é na escrita que é feito esta
alteração.

# Possiveis Melhorias

- Para possíveis melhorias, por se tratar de um serviço que é rodado diariamente e
trabalha com milhões de registros pode-se tentar implementar o SPRING BATCH.

- Melhorar a cobertura dos testes.
   
- Aplicar TDD para o as próximas melhorias.

# Tecnologias Utilizadas

- Java 11
- Spring BOOT
- Lombok
- Open CSV
- Junit e Mockito

# Executando o Projeto

Para executar o projeto será necessário o seguinte comando:
   - java -jar caminhoAondeFoiSalvadoOJar/sincronizacaoreceita.jar caminhoAondeEstaOCsv/contas.csv

Após a execução do comando o arquivo de contas processadas será criado
no seu diretório C:
