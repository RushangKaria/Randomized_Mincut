import java.io.*;
import java.util.*;

public class DataOfMinCut
{
	static InputStreamReader r;
	static BufferedReader input;

	Random gen;

	int E,V;
	//int graph[][];
	
	//-------------DEBUGGING WITH FAST INPUT---------------
	int graph[][]={			
			{0,1,0,1,0,1},
			{1,0,1,0,1,0},
			{0,1,0,1,0,1},
			{1,0,1,0,1,0},
			{0,1,0,1,0,1},
			{1,0,1,0,1,0}
		      };
	//-----------------------------------------------------

	DataOfMinCut()throws IOException
	{
	r=new InputStreamReader(System.in);
	input=new BufferedReader(r);

	gen=new Random();

	E=0;
	V=-1;

	echo("=============RANDOMIZED ALGORITHM [MIN CUT] ==================");

	V=6; //DEBUGGING WITH FAST INPUT
		driver();
	}

	void driver()throws IOException
	{

		//takeInput();
		showGraph("***************INITIAL GRAPH******************");

		randomize();
	}

	void randomize()throws IOException
	{
		boolean marked[]=new boolean[V];

		//int rounds=V; USELESS VARIABLE...REMOVED FROM WHILE LOOP CONDITION (ROUNDS>2)
		//		SINCE IT DID NOT YIELD THE CORRECT ANSWER AND WAS WRONG IN LOGIC
		//		SINCE RANDOM SELECTION MAY CAUSE NO PROGRESS BUT STILL COUNT
		//		AS A ROUND

		int count=-1;
		int v1=-1;
		int v2,in,out;
		int mod=V; //to constrain random to provide values within our bounds


			/*		NOT REQUIRED
			
				//--------ITERATION 1...SOME BACKGROUND IS REQUIRED
				v1=(int)gen.nextInt()%mod;

				if(v1<0)
				v1=~v1;
				//------------------------------------------------

			*/

			while(count!=2)
			{
				while(true)
				{
				v1=(int)gen.nextInt()%mod;

				if(v1<0)
				v1=~v1;

					if(!marked[v1])
					break;

				//echo("V1="+v1);	DEBUGGING
				}
				
				v2=v1;
			
				while(v1==v2)
				{	
				v2=(int)gen.nextInt()%mod;

				if(v2<0)
				v2=~v2;

					if(marked[v2])
					v2=v1;

				//echo("V2="+v2);	DEBUGGING
				}

				in=v1<v2?v1:v2;
				out=v1>v2?v1:v2;

				marked[out]=true;

			echo("Contracting edge "+v1+"-"+v2+" to "+in);

					contract(in,out);

			count=getCount(marked);				
			}

		deleteloops();

		showGraph("****************FINAL GRAPH********************");

		
	}

	void contract(int in,int out)throws IOException
	{
	//int x=0; DEBUGGING 

	//int temp[][]=new int[V][V]; USELESS VARIABLE..SINCE GRAPH ITSELF IS ENOUGH

		echo("OUT is"+out);
	
		for(int i=0;i<V;i++)
		{
		graph[i][in]+=graph[i][out];	
		graph[in][i]+=graph[out][i];
		
		graph[i][out]=0;
		graph[out][i]=0;
		}
		
		/*	TO CHECK STATUS AFTER EACH CONTRACT OPERATION

		showGraph("=====================================================");
	
		if(input.readLine().equals("yes"))
		x=1;

		*/

	}

	int getCount(boolean marked[])
	{
	int count=0;

		for(int i=0;i<V;i++)
		if(!marked[i])	//since we are counting nodes remaining to contract and
		count++;	//not the ones which have already been contracted
				//therefore !marked 
	return count;
	}
	void deleteloops()
	{
		for(int i=0;i<V;i++)
		graph[i][i]=0;
	}

	void takeInput()throws IOException
	{
	int v1=-1,v2=-1;

	echo("Enter the no. of vertices in the graph");
	V=Integer.parseInt(input.readLine());		
	graph=new int[V][V];
	initialize_graph(V);	//not required since by default JAVA does it..but still!!
							/* CAUTION...The vertices numbered must be from
							   0 since we are using 0 as the first index
							   into the matrix
							*/

	echo("Enter the respective connectivity of the vertices...Input -1 to stop");

		while(true)
		{
			echo("Enter the first vertice to be considered");
			v1=Integer.parseInt(input.readLine());

			echo("Enter the second vertice to be considered");
			v2=Integer.parseInt(input.readLine());

				if(v1<0 || v2<0)
				break;

			graph[v1][v2]=1;
			graph[v2][v1]=1;

			E++;
		}		
	}

	void initialize_graph(int size)
	{
		for(int i=0;i<size;i++)
			for(int j=0;j<size;j++)
			graph[i][j]=0;
	}


	void showGraph(String s)
	{
	echo(s);
	echoc("    ");
		for(int i=0;i<V;i++)
		echoc(""+i+"  ");
		echo("");
		echoc("-----");
		for(int i=0;i<3*V;i++)
		echoc("-");
		echo("");	

			for(int i=0;i<V;i++)
			{
			echoc(""+i+"|  ");

				for(int j=0;j<V;j++)
				{
				echoc(""+graph[i][j]+"  ");
				}
			echo("");
			}	

	echo("**********************************************");
	}



	void echo(String s)
	{
		System.out.println(s);
	}

	void echoc(String s)
	{
		System.out.print(s);
	}
}