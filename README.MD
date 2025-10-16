# Minecraft Message Mod

A Fabric mod for Minecraft 1.21 that demonstrates modern Java development with GUI, Protobuf serialization, network communication, and PostgreSQL database integration.

## 🚀 Features

- **Modern GUI** - Custom screen with text input and buttons
- **Protocol Buffers** - Efficient message serialization using Protobuf 3
- **Network Packets** - Client-to-server communication via Fabric API
- **Database Storage** - PostgreSQL integration with Hibernate ORM
- **Repository Pattern** - Clean data access layer with JPA
- **Production Ready** - Proper error handling and architecture

## 🛠️ Tech Stack

- **Minecraft**: 1.21 + Fabric Loader
- **Backend**: Java 21, Fabric API
- **Serialization**: Google Protobuf 3
- **Database**: PostgreSQL + Hibernate ORM 6
- **Architecture**: Repository Pattern, JPA Entities
- **Build Tool**: Gradle 8.14

## 📦 Installation

1. **Requirements**:
    - Minecraft 1.21
    - Fabric Loader
    - PostgreSQL database

2. **Database Setup**:
   ```sql
   CREATE DATABASE minecraft_mod;
   ```
3. **Configuration**:
Update database credentials in DatabaseManager.java

## 🎮 Usage
1. Launch Minecraft with the mod installed

2. Press M to open the message GUI

3. Type your message and click "Send"

4. Message is serialized via Protobuf, sent to server, and stored in PostgreSQL

## 🏗️ Architecture
```text
Client GUI → Protobuf Serialization → Network Packet → Server Handler → Database Repository
```
### Key Components:
- `TextInputScreen` - GUI layer with input validation

- `NetworkManager` - Handles Protobuf packet serialization

- `MessageRepository` - Data access layer (Repository pattern)

- `MessageEntity` - JPA entity for database mapping

- `DatabaseManager` - Database connection and transaction management

## 🔧 Development
### Build:
```bash
./gradlew build
```
### Run in development:
```bash
./gradlew runClient
```
### Project Structure:
```text
src/
├── main/java/com/dddga/messagemod/
│   ├── database/          # Hibernate entities and repositories
│   ├── network/           # Protobuf and packet handling
│   └── MessageMod.java    # Main mod initializer
├── client/java/.../gui/   # GUI components
└── main/proto/            # Protobuf schema definitions
```
## 📊 Database Schema
```sql
CREATE TABLE messages (
id SERIAL PRIMARY KEY,
uuid UUID NOT NULL,
text VARCHAR(256) NOT NULL
);
```
## 🎯 Learning Outcomes
- This project demonstrates:

- Modern Minecraft modding with Fabric

- Protocol Buffers for efficient data serialization

- Client-server architecture in game mods

- PostgreSQL integration with Hibernate ORM

- Repository pattern and clean architecture

- Production-grade Java development