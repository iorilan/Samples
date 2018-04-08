#pragma once

////节点类
class Node
{
public:
	////methods
	Node(void);
	Node(int data);
	~Node(void);

	/////members
	Node* next;
	int data;
};

////叶子节点类
class TNode
{
public:
	////methods
	TNode(void);
	TNode(int data);
	~TNode(void);

	/////members
	TNode* left;
	TNode* right;
	int data;
};

////链表类
class MyLinkTable{
public:
	//////methods
	void RemovdeAt(int position,Node* head);
	void Add(Node* node,Node* head);
	void AddAfter(int position,Node* head,Node* node);
	bool IsEmpty(Node* head);
	void PrintNodes(Node* head);
	/////members
	Node* head;
};

/////////////链栈类///////////////
class MyLinkStack{
public:
	////mothods
	void Push(Node* node,Node* top);
	Node* Pop(Node* top);
	bool IsEmpty(Node* top);
	void PrintStack(Node* top);

	////members
	Node* top;
};

//////////二叉搜索树///////////

class BTree{
public:
	////methods
	void InsertNode(TNode* node);
	void PrintNode(TNode* head);
	TNode* FindValue(TNode* head,int value);
	TNode* Find(TNode* head,TNode* node);
	bool IsEmpty(TNode* head);
	////members
	TNode* head;

};