# Snowball and Egg Knockback
A Minecraft mod that allows the adjustment of snowball, egg, and ender pearl knockback and damage; and adjust the pull strength of a fishing rod.

## Config
Values can be adjust via commands, run one of these commands to see their usage:

- `/help snowballkb`
- `/help eggkb` 
- `/help endearpearlkb`
- `/help bobberpull`

Values set via these commands will be saved to the config, which is stored in `<world>/snowballkb.json`.

The list of affected entities is controlled by datapack entity type tag, you can create a tag by making an `affected` entity type tag in the `snowballkb` namespace.

**File layout**
```
└── my_datapack
    ├── data
    │   └── snowballkb
    │       └── tags
    │           └── entity_type
    │               └── affected.json
    └── pack.mcmeta
```