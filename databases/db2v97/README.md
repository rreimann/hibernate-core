##Setup Matrix Tests against DB2 v9.7

#### Add DB2 JDBC Driver
* not provided due to licensing regulations
* download current version from: https://www-304.ibm.com/support/docview.wss?uid=swg21363866
* copy db2jcc4.jar to jdbc folder

        ├── db2v97
        │   ├── jdbc
        │   │   └── db2jcc4.jar

#### Configure DB properties
* edit hibernate.properties to match your environment

        ├── db2v97
        │   └── resources
        │       └── hibernate.properties

#### Run tests
* all tests
`./gradlew hibernate-core:matrix_db2v97`
* a single test
`./gradlew -Dmatrix_db2v97.single=BooleanTest hibernate-core:matrix_db2v97`

#### Further reading
* see https://github.com/rreimann/hibernate-core/blob/master/buildSrc/Readme.md for further details