package lab02;

import java.util.Comparator;

class TableComparator implements Comparator<Table>
{
	@Override
	public int compare(Table lhs, Table rhs)
	{
		return rhs.size - lhs.size;
	}
}