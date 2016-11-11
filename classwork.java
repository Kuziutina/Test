//and packages

public class calsswork {
	public static void main(String[] args) throws FileNotFoundException{

		Scanner sc = new Scanner(new File("rep.txt"));
		PrintWriter pw = new PrintWriter(new File("output.txt"), true);


		do {
			String s = sc.nextLine();
			String [] strings = s.split("%");
			String name = strings[0].substring(2);
			pw.println(name);
			pw.println(strings[1]); 
		}
		while (sc.hasNextLine());
		pw.close(); // we can use 'pw.flush();'
			
	}
}