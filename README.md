# 🪐 Orbit Project Architecture & Phase Plan

This document outlines the architecture vision and phased development plan for the Orbit app, including modular structure, features, and responsibilities across modules.

---

## 🧱 Project Architecture Overview

```
core/
├── data                # Repository implementations, sync, AI access
├── database            # Room DB, DAOs, Entities
├── navigation-contract # Navigation route + key (bridge)
├── ui-contract         # Screen Ui Config (bridge)

feature/
├── actions        # Actions (tasks) UI, logic, contact linking
├── contacts       # Contact-related UI, logic, use cases
├── insights       # Insights UI, use cases, tagging, linking
```

Each `feature` module follows clean architecture:
- `data/` → implements repositories, handles feature-specific persistence logic
- `domain/` → use cases, interfaces, models
- `presentation/` → ViewModels, UI state, Jetpack Compose screens

---

## ✅ Phase 1: Local-Only Functionality

**Goal:** Fully offline Orbit app with support for Contacts, Insights, and Actions using Room.

### 🧩 Core Modules

#### `core:database`
Handles all Room-related logic, including entity definitions and DAO interfaces.

- [x] Define `ContactEntity`, `InsightEntity`, `ActionEntity`
- [x] Create `ContactDao`, `InsightDao`, `ActionDao`
- [x] Configure `OrbitDatabase`

#### `core:data`
Responsible for data coordination, mapping, and shared logic.

- [x] Implement Repositories:
  - [x] `ContactRepositoryImpl`
  - [x] `InsightRepositoryImpl`
  - [x] `ActionRepositoryImpl`
- [x] Handle mapping: Entity ↔ Domain Model
- [x] Provide unified access point for features

---

### 📦 Feature Modules (Local Feature Implementation)

#### `feature:contacts`
- Domain Model: `Contact`
- Responsibilities: Contact management, linking to actions/insights
- UI Screens:
  - Contact list
  - Contact detail (view/edit with related actions & insights)
- Use Cases:
  - `GetContacts`, `SearchContacts`, `GetContactById`, `AddContact`, `UpdateContact`, `DeleteContact`

#### `feature:insights`
- Domain Model: `Insight`
- Responsibilities: Insight creation and linking to contacts or actions
- UI Screens:
  - Insight list
  - Insight detail
- Use Cases:
  - `GetInsights`, `GetInsightsForContact`, `GetInsightsForAction`, `AddInsight`, `UpdateInsight`, `DeleteInsight`

#### `feature:actions`
- Domain Model: `Action`
- Responsibilities: Action tracking (like to-dos), linkable to contacts or insights
- UI Screens:
  - Action list
  - Action detail
- Use Cases:
  - `GetActions`, `AddAction`, `UpdateAction`, `GetActionsForContact`, `DeleteAction`

---

## 🤖 Phase 2: Local AI Integration

**Goal:** Enable on-device AI assistance via text and speech input.

### 🧠 Local AI Features

#### Step 1: **Text-Based AI Input**
- [ ] Add new screen with a TextField and "Submit" button
- [ ] `AiParser` parses input into: `Contact`, `Insight`, or `Action`
- [ ] Display parsed result for review and confirmation
- [ ] Integrate with existing repositories to save data

#### Step 2: **Speech-to-Text Integration**
- [ ] Add mic button to assistant screen
- [ ] Use `whisper.cpp` or similar for on-device transcription
- [ ] Transcribed result sent to `AiParser`

#### Step 3: **Smart UI & Feedback**
- [ ] Show AI processing status (e.g., "Parsing...", "Done")
- [ ] Allow users to approve or discard AI suggestions
- [ ] Store recent inputs and parsed results for testing/debugging

### Supporting Code
- `core:data` will contain `AiParser`, `AiResult`, and relevant logic
- Optional: Create `feature:aiassistant` module for dedicated UI

---

## 🔄 Phase 3: Online Sync & Cloud Backup *(Planned)*

- Cloud sync & backup
- Conflict resolution & timestamp handling
- User settings for sync control
- Data encryption layer

---

## 🧪 Optional Future Phase: Multi-device + Auth

- User sign-in
- Account-based data isolation
- Cloud calendar or app integrations
- Analytics and activity history

---

## 📌 Notes

- Each feature module is decoupled and testable in isolation
- `core:data` will grow to support AI and sync logic
- `core:database` is a passive data layer
- Compose-based UI follows one-way data flow via ViewModel → State → UI

---
