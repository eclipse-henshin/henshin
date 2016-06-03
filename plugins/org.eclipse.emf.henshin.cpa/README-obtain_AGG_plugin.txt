>> How to obtain the required Bundle 'de.tuberlin.eecs.agg' ?<<

[ The following information stem from: https://wiki.eclipse.org/Orbit/FAQ#How_do_I_work_with_a_bundle_in_Orbit.3F ]

How do I work with a bundle in Orbit?
Since all of the interesting orbit work is done in branches, working with an orbited bundle can be confusing to the first-time user. 
But just follow these simple steps and you'll have it checked out into your workspace in no time!

1. Create a repository connection to the orbit repository (CVS URL is :pserver:anonymous@dev.eclipse.org/cvsroot/tools).
2. Navigate to HEAD/org.eclipse.orbit/<your_project> .
   - project: de.tuberlin.eecs.agg
3. Check your project out into your workspace.
4. Note that the project will be empty. (this is ok)
5. In the Navigator (or Package Explorer, etc) right-click on the project 
   and choose Replace With -> Another Branch or Version... and select the branch that you want to work with. (the branch names are the versions)
   - use <Refresh Tags> und use the latest version in <Branches>