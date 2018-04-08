// C++Lab2.cpp : Defines the entry point for the console application.
//

#include "stdafx.h"
#include <string>
#include <iostream>
#include "BaseClass.h"
#include "ChildClass1.h"
using namespace std;

#define IN
#define OUT

int add(const int n1,const int n2){return n1+n2;}
double add(double n1,double n2){return n1+n2;}


int _tmain(int argc, _TCHAR* argv[])
{
//	char* s = (char*)malloc(sizeof(char));
//cin.getline(s,50);
//
//int i ;
//int wc = 0;
//for(i = 0;i < strlen(s);i ++){
//	if(s[i] == ' ' || s[i] == '\n'){
//	wc++;
//	}
//}
//cout<<wc<<endl;
cout<<add(1,3)<<endl;
cout<<add(1.5,1.6)<<endl;
cin.get();
return 0;
}

