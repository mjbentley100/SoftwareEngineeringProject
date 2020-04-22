import mysql.connector as sql

def connectDB():
    db_connection = sql.connect(host='localhost', database='test_schema', user='test_user', password='test123')
    return db_connection
