package model.data_structures;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import junit.framework.TestCase;
import model.vo.VOMovingViolations;

public class TestTablaHash extends TestCase{
	private 	LinearProbing<Integer, ArregloDinamico<Integer>> s;
	private ArrayList<Integer> a;
	private ArregloDinamico<Integer> q;
	private ArregloDinamico<Integer> e;

	@Before
	public void setUp() throws Exception{
		System.out.println("Codigo de iniciacion");
		q = new ArregloDinamico<>(5);
		e = new ArregloDinamico<>(5);
		a = new ArrayList<>();
		a.add(1);
		a.add(1);
		a.add(1);
		a.add(1);
		a.add(2);
		a.add(2);
		a.add(3);
		a.add(4);
		a.add(5);
		a.add(5);
		a.add(5);
		//
		e.agregar(1);
		e.agregar(1);
		e.agregar(1);
		e.agregar(1);
		e.agregar(2);
		e.agregar(2);
		e.agregar(3);
		e.agregar(4);
		e.agregar(5);
		e.agregar(5);
		e.agregar(5);

		System.out.println("E"+e.darTamano());
		System.out.println("A"+a.size());
		s= new LinearProbing<>(12342);
		int contador=0;
		contador++;
		for (int k = 0; k < a.size()-1; k++) {
			contador++;
			int actual= a.get(k);
			int siguiente= a.get(k+1);
			if(actual*actual+2==siguiente*siguiente+2)
			{
				q.agregar(actual);
				if(contador==a.size())
				{
					q.agregar(siguiente);
					s.put(actual, q);
				}
			}
			else if(actual*actual+2!=siguiente*siguiente+2 )
			{
				q.agregar(actual);
				s.put(actual, q);
				q=null;
				q= new ArregloDinamico<>(100);
			}
		}
		for (int i = 0; i < s.get(5).darTamano(); i++) {
			System.out.println(s.get(5).darElem(i));
		}

	}


	@Test
	public void test() {


	}

}
