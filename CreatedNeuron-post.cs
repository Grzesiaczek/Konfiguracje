using System;
using System.Collections.Generic;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Brain3D
{
    class CreatedNeuron : CreatedElement
    {
        AnimatedNeuron neuron;
        int frame;

        public CreatedNeuron(AnimatedNeuron neuron)
        {
            this.neuron = neuron;
            element = neuron;
            created = false;
        }

	public CreatedNeuron()
	{
	    created = true;
	}

        public override void show()
        {
            neuron.create();
        }

	public void draw()
	{
	    neuron.draw();
	}

        public AnimatedNeuron Neuron
        {
            get
            {
                return neuron;
            }
        }

        public float Scale
        {
            set
            {
                neuron.Scale = value;
		int k = 4;
            }
        }

        public int Frame
        {
            get
            {
                return frame;
            }
            set
            {
                frame = value;
            }
        }
    }
}
