// mfc7View.h : interface of the Cmfc7View class
//


#pragma once


class Cmfc7View : public CView
{
protected: // create from serialization only
	Cmfc7View();
	DECLARE_DYNCREATE(Cmfc7View)

// Attributes
public:
	Cmfc7Doc* GetDocument() const;

// Operations
public:

// Overrides
public:
	virtual void OnDraw(CDC* pDC);  // overridden to draw this view
	virtual BOOL PreCreateWindow(CREATESTRUCT& cs);
protected:
	virtual BOOL OnPreparePrinting(CPrintInfo* pInfo);
	virtual void OnBeginPrinting(CDC* pDC, CPrintInfo* pInfo);
	virtual void OnEndPrinting(CDC* pDC, CPrintInfo* pInfo);

// Implementation
public:
	virtual ~Cmfc7View();
#ifdef _DEBUG
	virtual void AssertValid() const;
	virtual void Dump(CDumpContext& dc) const;
#endif

protected:

// Generated message map functions
protected:
	DECLARE_MESSAGE_MAP()
};

#ifndef _DEBUG  // debug version in mfc7View.cpp
inline Cmfc7Doc* Cmfc7View::GetDocument() const
   { return reinterpret_cast<Cmfc7Doc*>(m_pDocument); }
#endif

