// mfc7Doc.cpp : implementation of the Cmfc7Doc class
//

#include "stdafx.h"
#include "mfc7.h"

#include "mfc7Doc.h"

#ifdef _DEBUG
#define new DEBUG_NEW
#endif


// Cmfc7Doc

IMPLEMENT_DYNCREATE(Cmfc7Doc, CDocument)

BEGIN_MESSAGE_MAP(Cmfc7Doc, CDocument)
END_MESSAGE_MAP()


// Cmfc7Doc construction/destruction

Cmfc7Doc::Cmfc7Doc()
{
	// TODO: add one-time construction code here

}

Cmfc7Doc::~Cmfc7Doc()
{
}

BOOL Cmfc7Doc::OnNewDocument()
{
	if (!CDocument::OnNewDocument())
		return FALSE;

	// TODO: add reinitialization code here
	// (SDI documents will reuse this document)

	return TRUE;
}




// Cmfc7Doc serialization

void Cmfc7Doc::Serialize(CArchive& ar)
{
	if (ar.IsStoring())
	{
		// TODO: add storing code here
	}
	else
	{
		// TODO: add loading code here
	}
}


// Cmfc7Doc diagnostics

#ifdef _DEBUG
void Cmfc7Doc::AssertValid() const
{
	CDocument::AssertValid();
}

void Cmfc7Doc::Dump(CDumpContext& dc) const
{
	CDocument::Dump(dc);
}
#endif //_DEBUG


// Cmfc7Doc commands
