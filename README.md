# 🌌 Corus

Corus é um sistema de gerenciamento de documentos e usuários desenvolvido em Java como projeto acadêmico de **Métodos de Projetos de Software**. O sistema demonstra a aplicação prática de **padrões de design** e princípios de engenharia de software.

---

## 🎯 **Objetivo Atual**

✅ Sistema de gerenciamento que oferece:
- **Cadastro e autenticação** de usuários com validações robustas
- **Gerenciamento de documentos** associados aos usuários  
- **Sistema de permissões** diferenciado (Admin/Usuário)
- **Persistência de dados** flexível (memória/arquivo)
- **Funcionalidades avançadas** como histórico e restauração de estados

---

## 🏗️ **Arquitetura e Padrões Implementados**

### **📋 Padrões de Design (GoF)**
- **🔧 Singleton:** `FacadeSingletonController` - Instância única do controlador principal
- **🎭 Facade:** Simplifica acesso aos subsistemas de gerenciamento
- **🔄 Strategy/Adapter:** Sistema de autenticação extensível (`AdminAuthenticator`, `UserAuthenticator`)
- **📦 Repository:** Abstração da camada de dados com implementações flexíveis
- **💾 Memento:** Sistema de salvamento e restauração de estados

### **🗂️ Estrutura do Projeto**
```
📁 Corus/
├── 📄 Main.java (Ponto de entrada)
├── 📁 auth/ (Sistema de autenticação)
├── 📁 controle/ (Lógica de negócio)
├── 📁 entidade/ (Modelos de dados)
├── 📁 facade/ (Padrão Facade + Singleton)
├── 📁 infra/ (Camada de persistência)
├── 📁 memento/ (Padrão Memento)
├── 📁 ui/ (Interfaces de usuário)
├── 📁 utils/ (Validações e exceções)
└── 📁 teste/ (Classes de teste)
```

---

## 🚀 **Funcionalidades Implementadas**

### **🔐 Sistema de Autenticação**
- **Admin:** `admin/admin` - Acesso total ao sistema
- **Usuários:** Cadastro dinâmico com validações rigorosas
- **Validações:**
  - Login: Máximo 12 caracteres, apenas letras
  - Senha: Mínimo 8 caracteres, maiúscula, minúscula, número, caractere especial

### **👨‍💼 Funcionalidades de Administrador**
- ✅ Gerenciar usuários (adicionar, listar)
- ✅ Gerenciar documentos (adicionar, listar, remover)
- ✅ Visualizar estatísticas do sistema
- ✅ **Salvar estado do sistema** (Memento)
- ✅ **Restaurar último estado** (Memento)
- ✅ **Histórico de estados salvos** (Memento)

### **👤 Funcionalidades de Usuário**
- ✅ Criar documentos pessoais
- ✅ Listar apenas seus documentos
- ✅ Editar/excluir documentos próprios
- ✅ Controle rigoroso de propriedade

### **💾 Sistema de Persistência**
- **Usuários:** Memória (RAM) ou Arquivo (`usuarios.txt`)
- **Documentos:** Memória (implementação atual)
- **Estados:** Sistema Memento para backup/restauração

---

## 🛠️ **Tecnologias Utilizadas**

| **Componente** | **Tecnologia** |
| --- | --- |
| **Linguagem** | Java (Orientação a Objetos) |
| **Padrões** | Singleton, Facade, Strategy, Repository, Memento |
| **Persistência** | Arquivo de texto, Memória |
| **Interface** | Console (Terminal) |
| **Arquitetura** | Camadas (UI, Controle, Entidade, Infraestrutura) |

---

## 🎮 **Como Executar**

### **📋 Pré-requisitos**
- Java 8+ instalado
- Terminal/Prompt de comando

### **▶️ Execução**
```bash
# Navegar para o diretório do projeto
cd Corus

# Compilar o projeto
javac *.java

# Executar o sistema
java Main
```

### **🔐 Credenciais de Teste**
- **Administrador:** `admin` / `admin`
- **Usuários:** Cadastre novos usuários através do admin

---

## 📊 **Demonstração de Funcionalidades**

### **💾 Sistema Memento em Ação**
1. **Salvar Estado:** Admin pode criar pontos de restauração
2. **Fazer Alterações:** Adicionar/remover usuários e documentos
3. **Restaurar:** Voltar ao estado anterior com um clique
4. **Histórico:** Visualizar todos os estados salvos com timestamps

### **🔒 Validações Implementadas**
- **Login:** Não pode conter números, máximo 12 caracteres
- **Senha:** Complexidade alta com múltiplos critérios
- **Documentos:** Associação obrigatória a usuários existentes
- **Permissões:** Usuários só acessam seus próprios documentos

---

## 🎯 **Objetivos Acadêmicos Demonstrados**

✅ **Programação Orientada a Objetos**  
✅ **Padrões de Design (GoF)**  
✅ **Arquitetura em Camadas**  
✅ **Tratamento de Exceções**  
✅ **Abstração e Encapsulamento**  
✅ **Princípios SOLID**  
✅ **Validação e Segurança**  
✅ **Persistência de Dados**

---

## 👥 **Autores**

- **Yan Feitosa**
- **Ruanderson Gabriel**
- **Felipe Apolinário**
- **Dmitri Verdi**
- **Pedro Henrique Nogueira**


---

## � **Documentação Adicional**

- � `PDF-Projeto/` - Documentação detalhada do projeto
- 📁 `Diagramas/` - Diagramas de classes e arquitetura
- 📁 `teste/` - Classes de teste e validação

---

## 💡 **Status do Projeto**

🎉 **Concluído** - Sistema funcional com padrões de design implementados e validados.

