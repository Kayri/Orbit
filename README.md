# 🪐 Orbit Project Architecture & Phase Plan

This document outlines the architecture vision and phased development plan for the Orbit app, including modular structure, features, and responsibilities across modules.

---

## 🧱 Project Architecture Overview

```
core/
├── database       # Room DB, DAOs, Entities
├── data           # Repository implementations, sync, AI access

feature/
├── contacts       # Contact-related UI, logic, use cases
├── notes          # Notes UI, use cases, AI processing, etc.
├── tasks          # To-dos/reminders related to notes or contacts
```

Each `feature` module follows clean architecture:
- `data/` → implements repositories, handles feature-specific persistence logic
- `domain/` → use cases, interfaces, models
- `presentation/` → ViewModels, UI state, Jetpack Compose screens

---

## ✅ Phase 1: Local-Only Functionality

**Goal:** Fully offline Orbit app with support for Contacts, Notes, and Tasks using Room.

### 🧩 Core Modules

#### `core:database`
Handles all Room-related logic, including entity definitions and DAO interfaces.

- [x] Define `NoteEntity`, `ContactEntity`, `TaskEntity`
- [x] Create `NoteDao`, `ContactDao`, `TaskDao`
- [x] Configure `OrbitDatabase`

#### `core:data`
Responsible for data coordination, mapping, and shared logic (to be extended in Phase 2).

- [x] Implement Repositories:
  - [x] `NoteRepositoryImpl`
  - [x] `ContactRepositoryImpl`
  - [x] `TaskRepositoryImpl`
- [x] Handle mapping: Entity ↔ Domain Model
- [x] Provide unified access point for features & AI engine

---

### 📦 Feature Modules (Local Feature Implementation)

#### `feature:contacts`
- Domain Model: `Contact`
- Responsibilities: Contact management, owner links to notes/tasks
- UI Screens:
  - Contact list, detail (view/edit)
- Use Cases:
  - `GetContacts`, `SearchContacts`, `GetContactById`, `AddContact`, `UpdateContact`, `DeleteContact`

#### `feature:notes`
- Domain Model: `Note`
- Responsibilities: Note creation, linking to contacts, editable content
- UI Screens:
  - Note list, detail (with contact info)
- Use Cases:
  - `GetNotes`, `GetNoteByContactId`, `GetNoteById`, `AddNote`, `UpdateNote`, `DeleteNote`
- Planned:
  - Inline display of related tasks & owner info

#### `feature:tasks`
- Domain Model: `Task`
- Responsibilities: Task management (standalone or linked to notes/contacts)
- UI Screens:
  - Task list, detail, link to context
- Use Cases:
  - `GetTasks`, `AddTask`, `UpdateTask`, `GetTasksForNoteOrContact`,....

---

## 🧠 Phase 2: Local AI Integration

**Goal:** Integrate on-device AI to enrich user interaction and automate data handling.

### AI Engine (Local)
- [ ] Integrate speech-to-text
- [ ] Integrate `Mistral` for local LLM processing
- [ ] Add `AiParser` in `core:data` to:
  - [ ] Interpret natural input into structured notes/tasks/contacts
  - [ ] Auto-link related items
  - [ ] Suggest content based on previous data
- [ ] AI Use Cases:
  - `GenerateContactFromSpeech`, `GenerateNoteFromSpeech`, `GenerateTaskFromSpeech`
- [ ] Smart UI:
  - AI assistant screen with free-form input
  - Smart suggestions panel in detail screens

---

## 🔄 Phase 3: Online Sync & Cloud Backup

**Goal:** Add online sync, cloud storage, and optional account-based access.

- [ ] Add sync engine in `core:data`:
  - [ ] Track dirty state, manage timestamps
  - [ ] Push/pull changes with backend
  - [ ] Handle conflict resolution
- [ ] Configure remote provider (Firebase, Supabase, custom backend)
- [ ] Add backup/restore capability
- [ ] Add settings screen for sync control
- [ ] Add encryption layer for privacy before sync

---

## 🧪 Optional Future Phase: Multi-device + Auth

- [ ] Sign-in system (OAuth/Firebase/Supabase)
- [ ] Per-user data syncing
- [ ] Linked to Third-Party features (e.g., `Calendar`)
- [ ] Activity history or analytics

---

## 🚧 Notes

- Every feature module (`contacts`, `notes`, `tasks`) follows: `data/`, `domain/`, `presentation/`
- `core:data` handles shared responsibilities: sync, AI, cross-feature linking
- `core:database` is low-level persistence only (no app logic)
- ViewModels in `presentation` depend on `domain` use cases only
- Use UI-specific models for screens (e.g., `NoteDetailModel`) if needed

---
