import mysql.connector as sql

# Function that connects to MySQL database
def connectDB():
    db_connection = sql.connect(host='localhost', database='test_schema', user='test_user', password='test123')
    return db_connection
