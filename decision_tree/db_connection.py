class DBConnection:
    # Function for connecting to MySQL Database
    def connectDB(self):
        try:
            db_connection = sql.connect(host='localhost', database='test_schema', user='test_user', password='test123')
            return db_connection
        except:
            return -1
