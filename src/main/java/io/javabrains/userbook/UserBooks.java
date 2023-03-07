package io.javabrains.userbook;

import org.springframework.data.cassandra.core.mapping.*;

import java.time.LocalDate;

@Table(value="book_by_userid_and_bookid")
public class UserBooks
{
    @PrimaryKey
    private UserBooksPrimaryKey key;

    @Column("reading_status")
    @CassandraType(type = CassandraType.Name.TEXT)
    private String readingStatus;

    @Column("started_date")
    @CassandraType(type = CassandraType.Name.DATE)
    private LocalDate startedDate;

    @Column("end_date")
    @CassandraType(type = CassandraType.Name.DATE)
    private LocalDate endDate;

    @Column("rating")
    @CassandraType(type=CassandraType.Name.INT)
    private int rating;

    public UserBooksPrimaryKey getKey()
    {
        return key;
    }

    public void setKey(UserBooksPrimaryKey key)
    {
        this.key = key;
    }

    public String getReadingStatus()
    {
        return readingStatus;
    }

    public void setReadingStatus(String readingStatus)
    {
        this.readingStatus = readingStatus;
    }

    public LocalDate getStartedDate()
    {
        return startedDate;
    }

    public void setStartedDate(LocalDate startedDate)
    {
        this.startedDate = startedDate;
    }

    public LocalDate getEndDate()
    {
        return endDate;
    }

    public void setEndDate(LocalDate endDate)
    {
        this.endDate = endDate;
    }

    public int getRating()
    {
        return rating;
    }

    public void setRating(int rating)
    {
        this.rating = rating;
    }
}
