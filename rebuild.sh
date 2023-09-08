rm -rf "C:\Program Files (x86)\Apache Software Foundation\Tomcat 10.0\webapps\EJournal\WEB-INF\classes\org"
cp -r "C:\Users\Illia\IdeaProjects\EJournal\out\production\EJournal\org" "C:\Program Files (x86)\Apache Software Foundation\Tomcat 10.0\webapps\EJournal\WEB-INF\classes"
curl --user admin:admin http://localhost:8080/manager/text/reload?path=/EJournal