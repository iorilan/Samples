// c++Lab1.cpp : Defines the entry point for the console application.
//

#include "stdafx.h"
#include <iostream>
#include <string>
#include <list>
#include <algorithm>
#include "Node.h"

using namespace std;

////STL LAB
void SampleOfListFind();
void SampleOfListInsert();
void SampleOfListRemove();

void PrintElement(string& element)
{
cout<<element<<endl;
}

///////END///////////////

////Althrithm lab

////欧几里得算法(求两个数的最大公约数)
int God(int M,int N)
{
int rem ;
while(N > 0)
{
rem=M%N;
M=N;
N=rem;
}
return M;
}

////获得最大子序列的和，复杂度O(n)
int GetMaxSubSeq(int* arr,int len)
{
	////非法参数的情况
	if(arr==NULL)
	{
	return -1;
	}

	////全是负数的情况
int index = 0;
int maxNum = arr[0];
for(int i = 0;i<len;i ++){
if(arr[i] < 0)
{
index++;
maxNum = maxNum > arr[i] ? maxNum:arr[i];
}
else
{
break;
}
}
if(index == len)
{
return maxNum;
}

////不全是负数的情况

int sum = 0;
int max = 0;
for(int i=0;i < len;i ++)
{
	sum += arr[i];
max=max<sum?sum :max;
sum=sum>0?sum:0;
}

return max;
}


/////获得最大子序列的和,复杂度O(n^2)
int GetMaxSubSequence(int* arr,int len)
{
	int i, j;
    int curSum; /* 当前序列和 */
    int maxSum; /* 最大序列和 */

    /* 初始化最大子序列和为序列第一个元素 */
    maxSum = arr[0];

    /* 外层循环定义子序列起始位置 */
    for (i = 0; i < len; i++)
    {
        /* 起始位置为i，初始化当前和为0 */
        curSum = 0;

        /* 内层循环定义子序列结束位置 */
        for (j = i; j < len; j++)
        {
            /* 计算子序列和，并与最大子序列和比较，更新最大子序列和 */
            curSum = curSum + arr[j];

            /* 与最大子序列和比较，更新最大子序列和 */
            if (curSum > maxSum)
            {
                maxSum = curSum;
            }
        }
	}
		return maxSum;
}


int _tmain(int argc, _TCHAR* argv[])
{
///////////////////////link table test/////////////////
////创建链表
//MyLinkTable* lt = new MyLinkTable;
//lt->head = new Node;
//Node* n = lt->head;
//for(int i= 1 ;i < 10;i ++){
//Node* p = new Node(i);
//////在末尾追加节点
//lt->Add(p,lt->head);
//}
//
//////打印
//lt->PrintNodes(lt->head);
//
//////在第7个位置后面插入节点
//Node* sevenNode = new Node(77);
//lt->AddAfter(7,lt->head,sevenNode);
//////打印
//lt->PrintNodes(lt->head);
//////删除第五个节点
//lt->RemovdeAt(0,lt->head);
//////打印
//lt->PrintNodes(lt->head);

//////////////////////end////////////////


////////////////////////link stack test/////////////

//MyLinkStack* ls = new MyLinkStack;
//ls->top = new Node(0);
//for(int i = 1;i < 10;i ++)
//{
//ls->Push(new Node(i),ls->top);
//}
//
//ls->PrintStack(ls->top);
//Node* tmp = ls->Pop(ls->top);
//delete tmp;
//ls->PrintStack(ls->top);

///////////////////////end//////////////////

//////BTRee Test////////////////////////////

TNode *head = new TNode;
head->data = 7;

BTree *btree = new BTree;
btree->head = head;


btree->InsertNode(new TNode(3));
btree->InsertNode(new TNode(1));
btree->InsertNode(new TNode(2));
btree->InsertNode(new TNode(6));
btree->InsertNode(new TNode(4));
btree->InsertNode(new TNode(8));
btree->InsertNode(new TNode(9));




btree->PrintNode(btree->head);

TNode* targetNode = btree->FindValue(btree->head,9);
if(NULL == targetNode){
cout<<"can not found";
}
else{
cout<<targetNode->data<<endl;
}
/////////End/////////////////////////////////


cin.get();
	return 0;
}

/////////////////////STL////////////////////

///查找元素
///summary
///
void SampleOfListFind()
{
	////创建一个集合
list<string> fruitList;
////集合的迭代器
list<string>::iterator fruitIterator;
////放入一些元素
fruitList.push_back("apple");
fruitList.push_back("pineApple");
fruitList.push_back("starApple");
////试图查找一个元素，并把地址返回给迭代器
fruitIterator = find(fruitList.begin(),fruitList.end(),
					 "apple");

if(fruitIterator==fruitList.end()){
	////迭代器地址在集合尾部，提示没有找到
cout<<"not found"<<endl;
}
else{
	////输出找到的元素
cout<< *fruitIterator<<endl;
}

}

///////////
////summary
////插入元素
void SampleOfListInsert(){
////创建一个集合
list<string> fruitList;
////创建一个迭代器
list<string>::iterator fruitIterator;

////插入一些元素
fruitList.push_back("apple");
fruitList.push_back("pineApple");
fruitList.push_back("starApple");
fruitIterator = fruitList.begin();
fruitIterator=fruitIterator ++;
fruitList.insert(fruitIterator,"secondApple");



////使用for循环来验证结果
for(fruitIterator = fruitList.begin();fruitIterator!=
	fruitList.end();fruitIterator++){
cout<<*fruitIterator<<endl;
}

////排序
fruitList.sort();

////使用foreach循环打印元素
for_each(fruitList.begin(),fruitList.end(),PrintElement);

}

////summary
////移除元素
////////////
void SampleOfListRemove(){
////创建一个集合
list<string> fruitList;
////创建一个迭代器
list<string>::iterator fruitIterator;

////插入一些元素
fruitList.push_back("apple");
fruitList.push_back("pineApple");
fruitList.push_back("starApple");
fruitList.push_back("apple4");
fruitList.push_back("apple5");
fruitList.push_back("apple6");
fruitList.push_back("apple7");
fruitList.push_back("apple8");
fruitList.push_back("apple9");
fruitList.push_back("apple10");
fruitList.push_back("apple11");
fruitList.push_back("apple12");
////移除最后一个元素
fruitList.pop_back();
////移除第一个元素
fruitList.pop_front();
////给一个指针，移除指向的元素
fruitList.erase(++++fruitList.begin());
////移除指定元素
fruitList.remove("apple6");

for_each(fruitList.begin(),fruitList.end(),PrintElement);
}

/////////////////////////////END//////////////////