<div align="center">

![LOGO](https://cdn.modrinth.com/data/cached_images/c6b3e022a78b1506335add519bbc2af54a4379ba.png)
![MODRINTH](https://img.shields.io/modrinth/dt/ZfLBx8El?style=for-the-badge&logo=modrinth&logoColor=%2300AF5C&logoSize=auto&label=DOWNLOAD&color=%2300AF5C&link=https%3A%2F%2Fmodrinth.com%2Fproject%2FZfLBx8El)
![GITHUB](https://img.shields.io/badge/GITHUB-REPO-blue?style=for-the-badge&logo=github&label=GITHUB&link=https%3A%2F%2Fgithub.com%2FRunkang10%2FAtomicCrash)
![LICENSE](https://img.shields.io/badge/LICENSE-MIT-brightgreen?style=for-the-badge&link=https%3A%2F%2Fgithub.com%2FRunkang10%2FAtomicCrash%3Ftab%3DMIT-1-ov-file)

</div>

## How it works
AtomicCrash sends malformed packets with extreme values to the target player's client.
This may cause the client to crash, freeze, or disconnect depending on the client version,
mods installed, and hardware. Results are not guaranteed.

## Features
- **Simple to use**\
  One command with the player's name and done
- **Exemption system**\
  Only players with a higher exemption number can crash you
- **Simple API Support**
- **Folia support**
- **ViaVersion support**

## Limitations
As of now there are some limitations with this plugin:
- Bedrock players are fully exempt
- Due to the way how this plugin works, clients can install mods to block malformed packets

## Commands
| Command | Description |
|---|---|
| `/crash <target>` | Attempt to crash the target's Minecraft client |
| `/atomiccrash reload` | Reload configurations |

## Permissions
All permissions below are granted to **OP players** by default:
| Permission | Description |
|---|---|
| `atomiccrash.command.crash` | Required to crash players |
| `atomiccrash.command.atomiccrash` | Required to use `/atomiccrash` |
| `atomiccrash.command.atomiccrash.reload` | Required to reload |
| `atomiccrash.exempt.<number>` | Protects the player from being crashed. A player can only be crashed by someone with a **higher** exemption number than their own or if the command is executed by console. |
