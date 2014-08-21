package scalaz.stuff

import scalaz._
import Scalaz._

object tree {
  def freeTree: Tree[Char] =
    'P'.node(
      'O'.node(
        'L'.node('N'.leaf, 'T'.leaf),
        'Y'.node('S'.leaf, 'A'.leaf)),
      'L'.node(
        'W'.node('C'.leaf, 'R'.leaf),
        'A'.node('A'.leaf, 'C'.leaf)))            //> freeTree: => scalaz.Tree[Char]
  
  // loc returns a TreeLoc which is a zipper (represents a part of the tree)
  // Note that getChild returns an Option[TreeLoc[A]] so we need to use monadic chaining
  // >>= (flatMap).
  // getChild also usees 1.based indexing!
  
  freeTree.loc                                    //> res0: scalaz.TreeLoc[Char] = TreeLoc(<tree>,Stream(),Stream(),Stream())
  freeTree.loc.root.getLabel.some                 //> res1: Option[Char] = Some(P)
  freeTree.loc.getChild(2) >>= { _.getChild(1) }  //> res2: Option[scalaz.TreeLoc[Char]] = Some(TreeLoc(<tree>,Stream(),Stream(<tr
                                                  //| ee>, ?),Stream((Stream(<tree>, ?),L,Stream()), ?)))
  freeTree.loc.getChild(2) >>= { _.getChild(1) } >>= { _.getLabel.some }
                                                  //> res3: Option[Char] = Some(W)
  freeTree.loc.getChild(1) >>= { _.getChild(2) } >>= { _.getLabel.some }
                                                  //> res4: Option[Char] = Some(Y)
   
  val newFocus = freeTree.loc.getChild(2) >>= {_.getChild(1)} >>= {_.modifyLabel({_ => 'P'}).some}
                                                  //> newFocus  : Option[scalaz.TreeLoc[Char]] = Some(TreeLoc(<tree>,Stream(),Stre
                                                  //| am(<tree>, ?),Stream((Stream(<tree>, ?),L,Stream()), ?)))
  // Re-construct the tree after changes...
  newFocus.get.toTree                             //> res5: scalaz.Tree[Char] = <tree>
  
  newFocus.get.toTree.draw foreach { _.print }    //> "P""|""+- O""|  |""|  +- L""|  |  |""|  |  +- N""|  |  |""|  |  `- T""|  |""
                                                  //| |  `- Y""|     |""|     +- S""|     |""|     `- A""|""`- L""   |""   +- P"" 
                                                  //|   |  |""   |  +- C""   |  |""   |  `- R""   |""   `- A""      |""      +- A"
}