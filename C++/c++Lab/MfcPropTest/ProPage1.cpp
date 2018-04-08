

#include "stdafx.h"
#include "MfcPropTest.h"
#include "ProPage1.h"

// ProPage1 dialog

IMPLEMENT_DYNAMIC(ProPage1, CPropertyPage)

ProPage1::ProPage1()
	: CPropertyPage(ProPage1::IDD)
{

}

BOOL ProPage1::OnSetActive(){
((CPropertySheet*)this->GetParent())->SetWizardButtons(PSWIZB_NEXT);
return true;
}

ProPage1::~ProPage1()
{
}

void ProPage1::DoDataExchange(CDataExchange* pDX)
{
	CPropertyPage::DoDataExchange(pDX);
}


BEGIN_MESSAGE_MAP(ProPage1, CPropertyPage)
END_MESSAGE_MAP()


// ProPage1 message handlers
