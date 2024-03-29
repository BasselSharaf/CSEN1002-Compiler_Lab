package csen1002.main.task4;

import java.util.*;


public class FDFA {

	HashMap<Character, ArrayList<Character>> transitions;
	HashSet<String>accept;

	public FDFA(String description) {
		String[] split1= description.split("#");
		String[] fdfa = split1[0].split(";");
		transitions=new HashMap<>();
		for(String x : fdfa){

			ArrayList<Character> h = new ArrayList<>();
			h.add(x.charAt(2));
			h.add(x.charAt(4));
			h.add(x.charAt(6));
			transitions.put(x.charAt(0),h);
		}
		accept = new HashSet<>(Arrays.asList(split1[1].split(",")));
		System.out.println("Transitions: "+transitions);
		System.out.println("Accept States: "+accept);
	}




	public String run(String in) {
		StringBuilder output= new StringBuilder();
		int l=0;
		int r=0;
		Stack<Character> s =new Stack<>();
		s.push('0');
		Character qr;


		boolean first=true;
		do {
			// 1- Push all elements in the stack and move the L pointer on the tape
			while (l < in.length()) {

				s.push(transitions.get(s.peek()).get(Integer.parseInt(in.charAt(l) + "")));
				l++;
			}

			// 2- If last state is accept state
			if (first && accept.contains(s.peek() + "")) {
				output = new StringBuilder(in.substring(r, l) + "," + transitions.get(s.peek()).get(2) + ";");
				s.empty();
				System.out.println("exiting");
				return output.toString();
			}
			first = false;

			// 3- Continue to pop the stack until either the stack is empty or accept state is met
			qr = s.peek();
			while (!s.isEmpty()) {
				s.pop();
				l--;
				// Accept state is found
				if (!s.isEmpty() && accept.contains(s.peek() + "")) {
					// Output the string from R to L
					output.append(in, r, l).append(",").append(transitions.get(s.peek()).get(2)).append(";");
					// Move R to L
					r = l;

					// Empty the stack
					s = new Stack<>();
					// Insert Initial state
					s.push('0');
					// Exit the popping loop
					break;
				}
			}

			// 4- If r is at the end of the tape or the stack is empty exit the big loop
		} while (r != in.length() && !s.isEmpty());
		if(output.length()==0){
			output.append(in).append(",").append(transitions.get(qr).get(2)).append(";");
		}if(l<r&&r>0){
			output.append(in.substring(r)).append(",").append(transitions.get(qr).get(2)).append(";");
		}


		System.out.println(output);
		return output.toString();
	}

	public static void main (String[] args){
		FDFA fdfa1 = new FDFA("0,1,0,N;1,1,2,O;2,3,1,P;3,3,4,Q;4,3,4,A#4");
		String test1 = "10111";  //Output: "10111,P;"
		System.out.println(fdfa1.run(test1));

	}
}
