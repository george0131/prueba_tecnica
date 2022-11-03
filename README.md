# Prueba Técnica Alianza

Prueba Técnica Asesoftware

## Para empezar

Debes disponer de:

* Java 8
* BD PostgreSQL

Sugerimos crear la BD con docker, el comando a ejecutar es:
```
docker run --name alianza -p 54320:5432 -e POSTGRES_DB=development -e POSTGRES_USER=developer -e POSTGRES_PASSWORD=developer -d postgres:13.8
```

Clona el repositorio
```
git clone https://github.com/george0131/prueba_tecnica.git
```

Compilación del proyecto
```
mvn clean compile
```

Ejecución del proyecto
```
mvn spring-boot:run
```

Empaquetado del proyecto
```
mvn package
```