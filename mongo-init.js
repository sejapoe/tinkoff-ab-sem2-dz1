db.createUser(
    {
        user: "test",
        pwd: "secret",
        roles: [
            {
                role: "readWrite",
                db: "logs"
            }
        ]
    }
);