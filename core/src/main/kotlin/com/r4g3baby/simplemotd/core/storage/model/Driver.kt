package com.r4g3baby.simplemotd.core.storage.model

import java.util.*

enum class Driver(
    groupId: String, artifactId: String, version: String, encodedHash: String
) : com.r4g3baby.simplemotd.api.storage.model.Driver {
    H2(
        "com.h2database", "h2", "1.4.200",
        "OtmsS2qunNnTrBxEdGXh7QYBm4UbiT3WqNdt222FvKY="
    ),
    SQLite(
        "org.xerial", "sqlite-jdbc", "3.39.2.1",
        "80Qzj0lQ2H9D3PRA0YG6NdLqRStsV7uLxRbZkcJNxVY="
    ),
    PostgreSQL(
        "org.postgresql", "postgresql", "42.5.0",
        "pNGLWrGuuShaixezZfQk8mhEUinKv45BIRXbYVK33uM="
    ),
    MariaDB(
        "org.mariadb.jdbc", "mariadb-java-client", "3.0.7",
        "lse7LmIozL34jdSwcTRpefoSFhlqJKyj0fueQZlpi+8="
    ),
    MySQL(
        "mysql", "mysql-connector-java", "8.0.30",
        "tb8vCYcZfDCt90qeQZuJzaTCV9otEUKHH1CEFtXyIno="
    );

    override val fileName: String = "${name.lowercase()}-$version.jar"

    override val mavenPath: String = "%s/%s/%s/%s-%s.jar".format(
        groupId.replace(".", "/"), artifactId, version, artifactId, version
    )

    private val hash: ByteArray = Base64.getDecoder().decode(encodedHash)
    override fun validateHash(otherHash: ByteArray): Boolean {
        return hash.contentEquals(otherHash)
    }

    companion object {
        @JvmStatic
        fun fromValue(value: String): Driver? {
            for (driver in values()) {
                if (driver.name.equals(value, ignoreCase = true)) {
                    return driver
                }
            }
            return null
        }
    }
}