# .discord
**Klickbarer Discord-Link als Minecraft Plugin für Paper 1.21.1**

Verbinde deinen Discord-Server mit einem einzigen Befehl. Spieler tippen `/discord`, ein übersichtliches 3-reihiges Inventar öffnet sich mit einem leuchtenden Wissensbuch in der Mitte — ein Klick schickt einen anklickbaren Chat-Link direkt an den Spieler, inklusive sattem Ping-Sound. Alles ist vollständig konfigurierbar: Inventar-Titel, Item-Name, Chat-Nachricht, Discord-Link und Sound. Kein Ballast, pure Funktionalität.

---

## Features
- `/discord` öffnet ein individuelles 3-reihiges Inventar
- Wissensbuch mit dem Namen **Discord Server** (standardmäßig blau & fett)
- Klick auf das Buch: Ping-Sound + anklickbare Chat-Nachricht
- Hover über die Nachricht zeigt den Link als Vorschau
- Vollständig konfigurierbar über `.discord.yml` — Farben, Sounds, Link, Nachrichten

---

## Befehle & Berechtigungen

| Befehl | Berechtigung | Standard |
|---|---|---|
| `/discord` | `myplugin.discord` | Alle Spieler |

---

## Installation
1. JAR aus dem [Releases](../../releases)-Tab herunterladen
2. In den `plugins/`-Ordner deines Paper-Servers legen
3. Server starten — die `.discord.yml` wird automatisch erstellt
4. Discord-Link in `plugins/.discord/.discord.yml` eintragen
5. Server neu laden mit `/reload confirm`

---

## Konfiguration (`plugins/.discord/.discord.yml`)

```yaml
discord:
  link: "https://discord.gg/deinserver"

inventory:
  title: "&9&lDiscord"
  item-name: "&9&lDiscord Server"
  filler-material: "GRAY_STAINED_GLASS_PANE"

message:
  click-text: "&7Klicke &b&lhier &7um dem Discord beizutreten!"

sound:
  enabled: true
  type: "BLOCK_NOTE_BLOCK_PLING"
  volume: 1.0
  pitch: 2.0
```

> Farbcodes werden mit `&` geschrieben (z.B. `&9` = Blau, `&l` = Fett).

---

## Voraussetzungen
- Paper 1.21.1
- Java 21
