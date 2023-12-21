import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
public class XMLParser {
    public static void main(String[] args) {
        try {
            // Загрузка XML-файла
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse("xml_file.xml");

            // Создание корневого узла JTree
            Element rootElement = document.getDocumentElement();
            DefaultMutableTreeNode root = new DefaultMutableTreeNode(rootElement.getTagName());

            // Рекурсивное добавление узлов в JTree
            addNodes(rootElement, root);

            // Создание модели JTree
            DefaultTreeModel treeModel = new DefaultTreeModel(root);

            // Создание компонента JTree
            JTree tree = new JTree(treeModel);

            // Отображение JTree в окне
            JFrame frame = new JFrame("XML Viewer");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.add(new JScrollPane(tree));
            frame.pack();
            frame.setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Метод для рекурсивного добавления узлов в JTree
    private static void addNodes(Node parentNode, DefaultMutableTreeNode treeNode) {
        // Получение дочерних узлов
        NodeList nodeList = parentNode.getChildNodes();
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                // Добавление элемента в JTree
                Element element = (Element) node;
                DefaultMutableTreeNode childNode = new DefaultMutableTreeNode(element.getTagName());
                treeNode.add(childNode);
                // Рекурсивный вызов для добавления дочерних узлов
                addNodes(element, childNode);
            }
        }
    }
}
