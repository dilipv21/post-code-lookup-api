db.createUser(
    {
        user: "postalAdmin",
        pwd : "po$t@l@dm!n",
        roles: [ { role: "root", db: "admin" }]
    }
)

db.createUser(
    {
        user: "postalUser",
        pwd : "post@lU$er",
        roles: [ { role: "readWrite", db: "postal" }]
    }
)