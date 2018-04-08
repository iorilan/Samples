#include "StdAfx.h"
#include "Node.h"
#include <iostream>
/////////node methods/////////////
Node::Node(void)
{
	Node::data=0;
	Node::next=NULL;
}

Node::Node(int data)
{
	Node::data = data;
	Node::next=NULL;
}

Node::~Node(void)
{
}

//////////////////////end//////////////////

/////////Tnode methods/////////////
TNode::TNode(void)
{
	TNode::data=0;
	TNode::left=NULL;
	TNode::right=NULL;
}

TNode::TNode(int data)
{
	TNode::data = data;
	TNode::left=NULL;
	TNode::right=NULL;
}

TNode::~TNode(void)
{
}

//////////////////////end//////////////////


///////////linkTable methods//////////////


void MyLinkTable::RemovdeAt(int position,Node* head){
if(this->IsEmpty(head) || position < 0){
return;
}

////如果要删除头结点
if(position == 0)
{
Node* n = head;
head=head->next;
delete n;
MyLinkTable::head = head;
return;
}

Node* p = new Node;
p=head;
for(int i=0;i<position-1&&p!=NULL;p=p->next,i++);

if(p!=NULL)
{
Node* n = p->next;
p->next = p->next->next;
free(n);
}
}

void MyLinkTable::Add(Node* node,Node* head){
	if(this->IsEmpty(head))
	{
	return;
	}
Node* p = head;
while(p->next)
{
p=p->next;
}

p->next = node;
node->next = NULL;

}


void MyLinkTable::AddAfter(int position,Node* head,Node* node){
	if(this->IsEmpty(head)){
	return;
	}
Node* p = head;
for(int i = 0;i < position && p->next != NULL;p=p->next,i ++);

node->next = p->next;
p->next = node;

}

bool MyLinkTable::IsEmpty(Node* head){
return head==NULL;
}

void MyLinkTable::PrintNodes(Node* head){

	std::cout<<std::endl;
	while(head){
		std::cout<<head->data<<" ";
	head=head->next;
	}
	
}

///////////////end///////////////////


//////////MyLinkStack Methods/////////

void MyLinkStack::Push(Node* node,Node* top){
	if(this->IsEmpty(top))
	{
		return;
	}
node->next = top;
this->top = node;
}

Node* MyLinkStack::Pop(Node* top){
	if(this->IsEmpty(top))
	{
		return NULL;
	}

Node* tmp = top;
top=top->next;
MyLinkStack::top = top;
return tmp;
}

bool MyLinkStack::IsEmpty(Node* top){
return top==NULL;
}

void MyLinkStack::PrintStack(Node* top){
if(this->IsEmpty(top))
	{
		return;
	}

for(Node* tmp=this->top;tmp != NULL;tmp=tmp->next)
{
	std::cout<<tmp->data<<" ";
}
}
///////////////end////////////////////