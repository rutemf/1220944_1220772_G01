# WP2B Authors
## 1. Requirements Engineering
### 1.1. Customer Specifications and Clarifications

**From the [specifications document:](https://moodle.isep.ipp.pt/pluginfile.php/372607/mod_resource/content/0/PSOFT_LETI_assignment_2023-2024.pdf)**
>Central City library needs a system to manage their library, readers and book lending. The library consists
of thousands of books (no other media formats are available) organized by genre (e.g., Science-fiction,
mistery, Law, Medicine, etc.) that the readers can lend, take home and return after a period (typically 15
days). When a reader doesn’t return a book on time a fine will be applied per day of delay

**From the client clarifications:**

>[Q: Quais sao os critérios de aceitaçao do work package em questao?](https://moodle.isep.ipp.pt/mod/forum/discuss.php?d=29987)
>
>A: 
>3. As Librarian I want to register an author with an optional photo : os mesmos critérios do caso de uso do WP1 contemplando agora a possibilidade de adicionar uma imagem. 
>4. As Reader I want to know the books of an Author :
deve retornar a lista de livros desse autor ou uma lista vazia. devem ter em consideração paginação se a lista for demasiado longa
>5. As Reader I want to know the co-authors of an author and their respective books:
>deve retornar uma lista com os autores que escreveram livros em conjunto com um dado autor. para cada coautor deve ser retornada alista de livros escritos em conjunto. devem ter em consideração paginação se a lista for demasiado longa
>6. As Reader I want to know the Top 5 authors (which have the most lent books) :
>deve retornar os 5 autores com o maior numero de livros requisitados no ultimo ano



### 1.2. Found out Dependencies
- Books
- Lendings
- Authors

### 1.3. Acceptance Criteria
- As Librarian I want to register an author with an optional photo : os mesmos critérios do caso de uso do WP1 contemplando agora a possibilidade de adicionar uma imagem. 
- As Reader I want to know the books of an Author:
deve retornar a lista de livros desse autor ou uma lista vazia. devem ter em consideração paginação se a lista for demasiado longa
- As Reader I want to know the co-authors of an author and their respective books:
deve retornar uma lista com os autores que escreveram livros em conjunto com um dado autor. para cada coautor deve ser retornada alista de livros escritos em conjunto. devem ter em consideração paginação se a lista for demasiado longa
- As Reader I want to know the Top 5 authors (which have the most lent books) :
deve retornar os 5 autores com o maior numero de livros requisitados no ultimo ano


### 1.3. Functionality
### 1.8. Other Relevant Remarks
## 2. OO Analysis
### 2.1. Relevant Domain Model Excerpt
### 2.2. Other Remarks
## 3. Design
### 3.2. Class Diagram (CD)
## 4. Tests
## 5. Observations
