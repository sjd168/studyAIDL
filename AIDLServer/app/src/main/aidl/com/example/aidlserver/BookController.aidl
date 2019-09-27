// BookController.aidl
package com.example.aidlserver;
import com.example.aidlserver.Book;
// Declare any non-default types here with import statements

interface BookController {
List<Book> getBookList();
void addBookInOut(inout Book book);
}
