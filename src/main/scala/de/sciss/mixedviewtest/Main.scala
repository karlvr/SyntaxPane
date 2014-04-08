package de.sciss.mixedviewtest

import java.awt.EventQueue
import de.sciss.syntaxpane.{SyntaxView, DefaultSyntaxKit}
import javax.swing._
import javax.swing.text._

object Main extends App with Runnable {
  EventQueue.invokeLater(this)

  val contentType = "text/scalacollider"

  class TestView(elem: Element) extends PlainView(elem) {
    println("TestView: instantiated")
  }

  object TestFactory extends ViewFactory {
    def create(elem: Element): View = {
      println(s"TestFactory: create($elem)")
      new TestView(elem)
      // new SyntaxView(elem, DefaultSyntaxKit.getConfig(classOf[DefaultSyntaxKit]))
    }
  }

  class TestKit extends DefaultEditorKit {
    override def getContentType = contentType

    println("TestKit: instantiated")

    override def createDefaultDocument(): Document = {
      println("TestKit: createDefaultDocument")
      super.createDefaultDocument()
    }

    override def getViewFactory: ViewFactory = {
      println("TestKit: getViewFactory")
      TestFactory // super.getViewFactory
    }

    override def getActions: Array[Action] = {
      println("TestKit: getActions")
      super.getActions
    }
  }

  def mkEditor(): JComponent = {
    JEditorPane.registerEditorKitForContentType(contentType, classOf[TestKit].getName)
    DefaultSyntaxKit.initKit()
    val txt = """'''
                |code excerpt
                |'''
                |
                |# Yo crazy mama
                |""".stripMargin
    val ed = new JEditorPane(contentType, txt)
    val sp = new JScrollPane(ed)
    // ed.setContentType(contentType)
    // ed.setText(txt)
    sp
  }

  def run(): Unit = {
    val sp = mkEditor()
    val f = new JFrame("Foo")
    f.getContentPane.add(sp)
    f.setSize(400, 400)
    f.setLocationRelativeTo(null)
    f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE)
    f.setVisible(true)
  }
}
