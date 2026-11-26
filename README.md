# Vagabond – PROG3211 Final Project (Group 8)

Vagabond is an Android travel planner that turns a destination and a budget into a day-by-day trip plan.

Tourists spend hours researching what to see, how to get around and how much to budget. Vagabond puts this in one app so travellers get the most out of their time and money. It is designed for first-time visitors, budget travellers and students planning short trips (2–7 days).

## Key Features
- **Create a trip** – Enter a city, dates, budget and travel style (Budget / Mid-range / Luxury). The form is checked with Sealed Class validation, and errors are shown directly on the form.
- **Pick places** – Load popular attractions for the chosen city from the OpenTripMap API and select the ones to visit.
- **Day-by-day itinerary** – The app splits the selected places over the trip days (3–4 per day) and adds suggested transport and meal slots.
- **Budget estimate** – The app estimates stay, food, transport and ticket costs based on the travel style and compares the total with the user's budget.
- **Manage and sync trips** – View, edit and delete trips. Data is saved in SQLite first and then synced to Firebase Cloud Firestore.

> OpenTripMap does not provide prices or transport data, so all costs are estimates calculated by the app.

## Screens
| Screen | Type | Description |
|---|---|---|
| Splash | Required | Shows the logo and app name, then moves to Home |
| Home | Required | Upcoming trip card, city search, popular cities and the "+ New Trip" FAB |
| My Trips | Required | List of all saved trips (LazyColumn); tap to open, swipe to delete |
| Trip Detail | Required | Day-by-day itinerary and a Budget tab; edit or delete the trip |
| Add / Edit Trip | Extra | Trip form with input validation |
| Pick Places | Extra | API results with checkboxes and a "Generate plan" button |
| Explore | Extra | Browse attractions by city |
| Place Detail | Extra | Photo, category, rating and description of a place |
| Profile / Settings | Extra | User info and preferences |

**Navigation:** Bottom tab bar for the main sections and stack navigation for detail screens (Navigation Compose).

**Trip creation flow:** + New Trip (FAB) → Trip form → Validation → Pick Places → Generate plan → Save → Trip Detail

## Tech Stack
| Area | Technology |
|---|---|
| UI | Kotlin, Jetpack Compose, Material Design 3 with light and dark themes; all UI text in `strings.xml` |
| Architecture | MVVM: Compose screens → ViewModels (StateFlow) → Repositories → Data sources |
| Local storage | SQLite with `Trip` and `PlanItem` tables |
| Cloud storage | Firebase Cloud Firestore; SQLite is written first, then synced |
| REST API | [OpenTripMap API](https://dev.opentripmap.com) via Retrofit (city search, nearby attractions, place details) |
| Validation | Sealed Class results (Success / Error) for the trip form; sealed UI state (Loading / Success / Error) for API calls |
| Business logic | `ItineraryPlanner` splits places into days; `BudgetCalculator` estimates costs |

## Getting Started
1. Clone the repository and open it in Android Studio.
2. Add your OpenTripMap API key to `local.properties`: `OPENTRIPMAP_API_KEY=your_key`
3. Add the team's `google-services.json` file to the `app/` folder.
4. Sync Gradle and run the app on an emulator or a physical device.

## Team
| Name | Role |
|---|---|
| Hai Dang Tran | Data layer and Trip CRUD |
| Hunter Choi | UI design, theme and navigation |
| Pratham Mewada | REST API and place screens |
| Alexander Walker | Validation, Firestore and trip plan |

## Git Workflow
- `main` must always build.
- Create one branch per feature: `feature/<name>`, linked to a GitHub Issue.
- Merge into `main` only through a Pull Request reviewed by another member.

| Checkpoint | Due date | Goal | Tag |
|---|---|---|---|
| M1 | Nov 21, 2026 | Repository setup, README, starter project, issues assigned | `m1` |
| M2 | Nov 28, 2026 | Data layer, theme, navigation, API service, trip form | `m2` |
| M3 | Dec 5, 2026 | All screens working, Firestore sync, itinerary and budget | `m3` |
| M4 | Dec 13, 2026 | All work merged, final release and report | `v1.0.0` |

## UI/UX Design (Figma)
You can view the full Figma wireframes and design specifications in the attached PDF document:
- [View Figma Wireframes (PDF)](./figma_design.pdf)
