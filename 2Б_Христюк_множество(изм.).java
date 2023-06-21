public class Main
{
	public static void main(String[] args) {
public class Set : IEnumerable
    where T: IComparable
{
    private readonly List _items = new List();
 
    public Set()
    {
    }
 
    public Set(IEnumerable items)
    {
        AddRange(items);
    }
 
    public void Add(T item)
{
    if (Contains(item))
    {
        throw new InvalidOperationException(
"Item already exists in Set"
);
    }
 
    _items.Add(item);
}
 
    public void AddRange(IEnumerable items)
{
    foreach (T item in items)
    {
        Add(item);
    }
}
 
    public bool Remove(T item)
{
    return _items.Remove(item);
}
 
    public bool Contains(T item)
{
    return _items.Contains(item);
}
 
    public int Count
{
    get
    {
        return _items.Count;
    }
}
 
    public IEnumerator GetEnumerator()
{
    return _items.GetEnumerator();
}
 
System.Collections.IEnumerator System.Collections.IEnumerable.GetEnumerator()
{
    return _items.GetEnumerator();
}
 
    System.Collections.IEnumerator
System.Collections.IEnumerable.GetEnumerator();
}
	}
}
