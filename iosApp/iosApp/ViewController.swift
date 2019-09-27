//
//  ViewController.swift
//  iosApp
//
//  Created by Paula Guatura on 25/09/19.
//  Copyright © 2019 Paula Guatura. All rights reserved.
//

import UIKit
import api

class ViewController: UIViewController {
    let api = ApplicationApi()

    override func viewDidLoad() {
        super.viewDidLoad()
        
        
        api.jobs { (description) in
            let label = UILabel(frame: CGRect(x: 0, y: 0, width: 300, height: 500))
            label.backgroundColor = UIColor.blue
            label.center = CGPoint(x: 160, y: 285)
            label.textAlignment = .center
            label.numberOfLines = 0
            label.font = label.font.withSize(25)
            label.text = description.replacingOccurrences(of: "^\\s*", with: "", options: .regularExpression)
            self.view.addSubview(label)
        }
    }


}

