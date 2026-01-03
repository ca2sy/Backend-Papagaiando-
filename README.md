# 🦜 Papagaiando API

API REST para aplicação de Comunicação Aumentativa e Alternativa (CAA) voltada para pessoas com Transtorno do Espectro Autista (TEA).

## ⚠️ AVISO IMPORTANTE - VERSÃO DE DEMONSTRAÇÃO

**Este projeto está em fase de desenvolvimento e é apenas uma versão de demonstração, feito para um trabalho acadêmico**

❌ **NÃO deve ser utilizado para:**
- Fins terapêuticos
- Uso clínico
- Substituição de ferramentas profissionais de CAA
- Uso diário sem acompanhamento profissional

✅ **Ideal para:**
- Estudos e pesquisas
- Demonstrações de conceito
- Testes de viabilidade
- Desenvolvimento e aprimoramento


## 📋 Sobre o Projeto

Papagaiando é uma plataforma de **Comunicação Aumentativa e Alternativa (CAA)** desenvolvida para auxiliar pessoas com **Transtorno do Espectro Autista (TEA)** e outras condições que afetam a comunicação verbal.

### O que é CAA?

A Comunicação Aumentativa e Alternativa é um conjunto de ferramentas e estratégias que ajudam pessoas com dificuldades de fala a se comunicarem. Isso pode incluir:
- 🖼️ **Símbolos pictográficos** (imagens que representam palavras)
- 🔊 **Saída de voz** (áudio que reproduz a mensagem)
- 📱 **Aplicativos e dispositivos** adaptados

### Como funciona o Papagaiando?

A aplicação permite que:
1. **Usuários** criem perfis personalizados, diferentes perfis, como na netflix
2. **Categorias** organizem os botões por temas (alimentação, sentimentos, atividades, etc.)
3. **Botões** com imagens e áudios expressem necessidades e desejos
4. **Personalização** seja feita de acordo com as necessidades individuais de cada pessoa, de cada perfil

### Recursos principais:
- 🎨 Botões totalmente personalizáveis com imagens e áudios
- 📂 Organização por categorias temáticas
- 👥 Múltiplos perfis por usuário (família, escola, terapia)
- 🔒 Sistema seguro com autenticação
- ☁️ Acesso remoto aos recursos salvos

## 🚀 Tecnologias

- **Java 17**
- **Spring Boot 3.x**
- **Spring Security** com JWT
- **PostgreSQL**
- **Maven**
- **Docker**

## 📦 Estrutura do Projeto

```
src/main/java/com/papagaiando/Papagaiando/
├── controller/          # Endpoints REST
├── service/            # Lógica de negócio
├── repository/         # Acesso ao banco de dados
├── model/              # Entidades JPA
├── dto/                # Data Transfer Objects
├── security/           # Configurações de segurança e JWT
└── exception/          # Tratamento de exceções
```

## 🔑 Principais Funcionalidades

### Autenticação
- Registro de usuários
- Login com JWT
- Recuperação de senha
- Verificação de senha

### Usuários
- Gerenciamento de perfil próprio
- Atualização de dados
- Exclusão de conta

### Perfis
- Criação de múltiplos perfis por usuário
- Gerenciamento de perfis personalizados
- Upload de fotos de perfil

### Categorias
- Categorias padrão (disponíveis para todos)
- Categorias personalizadas por perfil
- Busca por nome
- Visualização combinada (padrão + personalizadas)

### Botões
- Botões padrão (pré-configurados)
- Botões personalizados com imagem e áudio
- Organização por categorias
- Upload de imagens e áudios

## 🛠️ Configuração e Instalação

### Pré-requisitos

- Java 17+
- Maven 3.8+
- PostgreSQL 14+
- Docker (opcional)


## 🔐 Segurança

### Autenticação JWT

Todos os endpoints (exceto login e registro) requerem autenticação via JWT.

**Header de autorização:**
```
Authorization: Bearer {seu-token-jwt}
```

### Autorização

O sistema implementa autorização baseada em propriedade:
- Usuários só podem acessar e modificar seus próprios recursos
- Perfis, categorias e botões são validados por propriedade
- Botões e categorias padrão são compartilhados entre todos os usuários


## 🗄️ Sobre Armazenamento de Arquivos

### Configuração Atual

O projeto utiliza:
- **Banco de dados PostgreSQL**: para armazenar dados (usuários, perfis, categorias, botões)
- **Supabase Storage**: para armazenar arquivos (imagens e áudios)
