package nz.co.xingsoft.memribox.server.common;

public class QueryRange {
    private int startRow;
    private int maxResults;

    public QueryRange() {
    }

    public QueryRange(final int startRow, final int maxResults) {
        super();
        this.startRow = startRow;
        this.maxResults = maxResults;
    }

    public int getStartRow() {
        return startRow;
    }

    public void setStartRow(final int startRow) {
        this.startRow = startRow;
    }

    public int getMaxResults() {
        return maxResults;
    }

    public void setMaxResults(final int maxResults) {
        this.maxResults = maxResults;
    }
}
