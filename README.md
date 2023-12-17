
# Google Home automations

Project that contains functionalities to create automations for Google Home.

Example invocations:

```bash
GOOGLE_AUTH_TOKEN=<GOOGLE_AUTH_TOKEN> \
HOUSE_ID=<HOUSE_ID> \
./gradlew run --args=../../automations
```

to avoid the recreation of the automations and execute just a local dry run use:

```bash
DRY_RUN=true \
./gradlew run --args=../../automations
```