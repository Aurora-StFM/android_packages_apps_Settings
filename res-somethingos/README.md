# res-somethingos

SomethingOS overrides of **SettingsLib** resources, to restyle Settings like
NothingOS without forking `frameworks/base`. A resource here with the same
name (and the same qualifier, e.g. `values-v36`) as one in a SettingsLib
library replaces it, because an app's own resources win over its libraries'.

Only overrides of library resources go here. SomethingOS's own new
resources are named `somethingos_*` and live in `res/`.
