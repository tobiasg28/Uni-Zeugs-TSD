
Software Architecturen, SS2010, TU Wien - Lab 2
-----------------------------------------------

DUML Alexander
GRAF Tobias
PERL Thomas
SCHENNER Dominik
WIESER Martin

=================================================
Einrichtung von PostgreSQL 8.4 unter Ubuntu 10.10
=================================================

Pakete installieren:
$ apt-get install postgresql-8.4 postgresql-client-8.4

Dann zum User "postgres" wechseln und den "swa"-User anlegen:
$ sudo -u postgres sh
$ createuser swa -P
Enter password for new role: swa11
Enter it again: swa11
Shall the new role be a superuser? (y/n) y

Dann die Datenbank anlegen:
$ createdb swa

(dann exit, d.h. die subshell vom postgres-User verlassen)

in /etc/postgresql/8.4/main/pg_hba.conf "ident" auf md5 ändern (2x)

Den Datenbankserver neu starten:
$ sudo service postgresql restart

Danach sollte man sich mit:

$ psql swa swa

einloggen können (Passwort = swa11 wie in der Angabe!)


======================================================
Generieren der Model-Klassen + DAOs + Unit-Tests + ...
======================================================

Das Projekt unter Eclipse öffnen.

Die Datei src/workflow/generator.mwe auswählen und mit

  "Run as MWE Workflow"

ausführen -> Dateien werden erstellt:

  Entities in src-gen/
  DAOs in src-gen/
  Unit-Tests in src-gen/
  drop-tables.sql im Hauptverzeichnis
  persistence.xml in bin/META_INF/

Danach kann man die Tests mit

  "Run as JUnit Test"

ausführen - das erstellt beim ersten Benutzen die Tabellen.


