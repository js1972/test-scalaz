package scalaz.stuff

import scalaz._
import Scalaz._
import java.util.{Date, Calendar}

object endo_dsl {
  case class Task(name: String) {
    def dependsOn(on: Task): Writer[Endo[Project], Task] = {
      for {
        _ <- WriterT.tell( ((p: Project) => withDependency(this, on, p)).endo )
      } yield this
    }
  }
  
  case class Project(name: String,
                     startDate: Date,
                     endDate: Option[Date] = None,
                     tasks: List[Task] = List(),
                     deps: List[(Task, Task)] = List());import org.scalaide.worksheet.runtime.library.WorksheetSupport._; def main(args: Array[String])=$execute{;$skip(628); 
  
  val withTask = (t: Task, p: Project) => p.copy(tasks = t :: p.tasks);System.out.println("""withTask  : (scalaz.stuff.endo_dsl.Task, scalaz.stuff.endo_dsl.Project) => scalaz.stuff.endo_dsl.Project = """ + $show(withTask ));$skip(91); 
  val withDependency = (t: Task, on: Task, p: Project) => p.copy(deps = (t, on) :: p.deps);System.out.println("""withDependency  : (scalaz.stuff.endo_dsl.Task, scalaz.stuff.endo_dsl.Task, scalaz.stuff.endo_dsl.Project) => scalaz.stuff.endo_dsl.Project = """ + $show(withDependency ));$skip(171); 
  
  def task(n: String): Writer[Endo[Project], Task] = {
    val t = Task(n)
    for {
      _ <- WriterT.tell( ((p: Project) => withTask(t, p)).endo )
    } yield t
  };System.out.println("""task: (n: String)scalaz.Writer[scalaz.Endo[scalaz.stuff.endo_dsl.Project],scalaz.stuff.endo_dsl.Task]""");$skip(141); 
  
  def project(name: String, startDate: Date)(e: Writer[Endo[Project], Task]) = {
    val p = Project(name, startDate)
    e.run._1(p)
  };System.out.println("""project: (name: String, startDate: java.util.Date)(e: scalaz.Writer[scalaz.Endo[scalaz.stuff.endo_dsl.Project],scalaz.stuff.endo_dsl.Task])scalaz.stuff.endo_dsl.Project""");$skip(338); 
  
  
  def mainProgram = {
    val now = Calendar.getInstance().getTime()
    val p = project("xenos", now) {
      for {
        a <- task("study requirements")
        b <- task("do analysis")
        _ <- b dependsOn a
        c <- task("design & code")
        _ <- c dependsOn b
        d <- c dependsOn a
      } yield d
    }
  };System.out.println("""mainProgram: => Unit""")}
}
