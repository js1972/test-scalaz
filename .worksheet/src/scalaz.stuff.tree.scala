package scalaz.stuff

import scalaz._
import Scalaz._

object tree {;import org.scalaide.worksheet.runtime.library.WorksheetSupport._; def main(args: Array[String])=$execute{;$skip(297); 
  def freeTree: Tree[Char] =
    'P'.node(
      'O'.node(
        'L'.node('N'.leaf, 'T'.leaf),
        'Y'.node('S'.leaf, 'A'.leaf)),
      'L'.node(
        'W'.node('C'.leaf, 'R'.leaf),
        'A'.node('A'.leaf, 'C'.leaf)));System.out.println("""freeTree: => scalaz.Tree[Char]""");$skip(250); val res$0 = 
  
  // loc returns a TreeLoc which is a zipper (represents a part of the tree)
  // Note that getChild returns an Option[TreeLoc[A]] so we need to use monadic chaining
  // >>= (flatMap).
  // getChild also usees 1.based indexing!
  
  freeTree.loc;System.out.println("""res0: scalaz.TreeLoc[Char] = """ + $show(res$0));$skip(34); val res$1 = 
  freeTree.loc.root.getLabel.some;System.out.println("""res1: Option[Char] = """ + $show(res$1));$skip(49); val res$2 = 
  freeTree.loc.getChild(2) >>= { _.getChild(1) };System.out.println("""res2: Option[scalaz.TreeLoc[Char]] = """ + $show(res$2));$skip(73); val res$3 = 
  freeTree.loc.getChild(2) >>= { _.getChild(1) } >>= { _.getLabel.some };System.out.println("""res3: Option[Char] = """ + $show(res$3));$skip(73); val res$4 = 
  freeTree.loc.getChild(1) >>= { _.getChild(2) } >>= { _.getLabel.some };System.out.println("""res4: Option[Char] = """ + $show(res$4));$skip(104); 
   
  val newFocus = freeTree.loc.getChild(2) >>= {_.getChild(1)} >>= {_.modifyLabel({_ => 'P'}).some};System.out.println("""newFocus  : Option[scalaz.TreeLoc[Char]] = """ + $show(newFocus ));$skip(66); val res$5 = 
  // Re-construct the tree after changes...
  newFocus.get.toTree;System.out.println("""res5: scalaz.Tree[Char] = """ + $show(res$5));$skip(50); 
  
  newFocus.get.toTree.draw foreach { _.print }}
}
